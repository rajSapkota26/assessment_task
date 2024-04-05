package com.example.assessment.feature.dashboard.vm

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.feature.dashboard.event.DashEvent
import com.example.assessment.feature.dashboard.state.DashState
import com.example.assessment.feature.login.event.LoginEvent
import com.example.assessment.service_layer.repository.RoomRepository
import com.example.assessment.utils.generic.BaseResponse
import com.example.assessment.utils.session.SessionManager
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import timber.log.Timber

class DashBoardVM(private var application: Application, private var repository: RoomRepository) :
    ViewModel() {

    var state by mutableStateOf(DashState())

    fun onEvent(event: DashEvent) {
        when (event) {
            is DashEvent.OnTransactionAddDialogOpen -> {
                state = state.copy(
                    isTransactionAddAndEditDialogShow = true,
                    isTransactionEdit = event.isEdit,
                    isTransactionDetailShown = false,
                    selectedData = null,
                    editData = if (event.isEdit) event.value else null
                )
            }

            is DashEvent.OnTransactionAddDialogCLose -> {
                state = state.copy(
                    isTransactionAddAndEditDialogShow = false,
                    isTransactionEdit = false,
                    isTransactionDetailShown = false,
                    selectedData = null,
                    editData = null

                )
            }


            is DashEvent.OnTransactionDetailDialogOpen -> {
                state = state.copy(
                    isTransactionDetailShown = true,
                    selectedData = event.value
                )
            }

            is DashEvent.OnTransactionDetailDialogClose -> {
                state = state.copy(
                    isTransactionDetailShown = false,
                    selectedData = null,
                    editData = null
                )
            }

            is DashEvent.OnTransactionDetailDelete -> {
                state = state.copy(
                    isTransactionDetailShown = false,
                    selectedData = null,
                    editData = null
                )
                val data = event.value
                //delete data from database
                //call init
                deleteData(data)
            }

            is DashEvent.OnTransactionDetailAdd -> {

                state = state.copy(
                    isTransactionAddAndEditDialogShow = false,
                    isTransactionEdit = false,
                    isTransactionDetailShown = false,
                    selectedData = null,
                    editData = null
                )
                val data = event.value
                val isEdit = event.isEdit
                if (isEdit) {
                    updateDate(data)
                } else {
                    addData(data)
                }
                //add data to database
                //call init
            }


            is DashEvent.OnSettingDialogOpen -> {
                state = state.copy(isSettingDialogShown = true)
            }

            is DashEvent.OnSettingDialogClose -> {
                state = state.copy(isSettingDialogShown = false)

            }

            is DashEvent.OnLogOut -> {
                //clear all session and redirect to login
                val sharedPreferencesManager = SessionManager(application)
                sharedPreferencesManager.deleteSession()
                state = state.copy(onLogoutSuccess = true)
            }
            is DashEvent.OnDeleteAccount -> {
                //currently not delete just log out
                val sharedPreferencesManager = SessionManager(application)
                sharedPreferencesManager.deleteSession()
                state = state.copy(onLogoutSuccess = true)
            }

            is DashEvent.OnStopErrorDialogue -> {
                state = state.copy(serverError = null)
            }

            is DashEvent.OnInit -> {
                state = DashState(isLoading = true)
                //get all records and calculate total income, expense and total balance
                getAllData()
                getSession()
            }

        }
    }

    private fun getSession() {
        val sharedPreferencesManager = SessionManager(application)
        val session = sharedPreferencesManager.getSession()
        state = state.copy(session = session)
    }

    private fun deleteData(data: IAndORecord) {
        viewModelScope.launch {
            viewModelScope.launch {
                repository.deleteTransaction(data).collect {
                    if (it.status == BaseResponse.Status.SUCCESS) {
                        state = state.copy(onLogoutSuccess = true)

                    } else {
                        responseToState(it)
                    }
                }
            }
        }

    }

    private fun getAllData() {
        viewModelScope.launch {
            viewModelScope.launch {
                repository.getAllTransaction().collect { response ->
                    delay(1000)
                    if (response.status == BaseResponse.Status.SUCCESS) {
                        state = state.copy(isLoading = false, allRecords = response.data)
                        response.data?.let {
                            calculateInComeExpense(it)

                        }

                    } else {
                        responseToState(response)
                    }
                }
            }
        }
    }

    private fun calculateInComeExpense(data: List<IAndORecord>) {
        var totalIncome = 0.0
        val incomeList = data.filterList {
            this.transactionType == "Income"
        }
        incomeList.forEach {
            totalIncome += it.amount
        }
        var totalExpense = 0.0
        val expenseList = data.filterList {
            this.transactionType == "Expense"
        }
        expenseList.forEach {
            totalExpense += it.amount
        }
        val balance = totalIncome - totalExpense
        state = state.copy(
            totalBalance = "$balance",
            totalExpense = "$totalExpense",
            totalIncome = "$totalIncome"
        )

    }

    private fun updateDate(data: IAndORecord) {
        viewModelScope.launch {
            viewModelScope.launch {
                repository.upDateTransaction(data).collect {
                    delay(1000)
                    if (it.status == BaseResponse.Status.SUCCESS) {
                        state = state.copy(isLoading = false)
                        onEvent(DashEvent.OnInit)

                    } else {
                        responseToState(it)
                    }
                }
            }
        }

    }

    private fun addData(data: IAndORecord) {
        viewModelScope.launch {
            repository.insertTransaction(data).collect {
                delay(1000)
                if (it.status == BaseResponse.Status.SUCCESS) {
                    state = state.copy(isLoading = false)
                    onEvent(DashEvent.OnInit)

                } else {
                    responseToState(it)
                }
            }
        }

    }

    private fun <T> responseToState(it: BaseResponse<T>) {
        state = state.copy(isLoading = false)
        Timber.v("it.error ${it.error} ${it.status}")
        when (it.status) {
            BaseResponse.Status.SUCCESS -> {
                // showProgress(false)
                //already handle above
            }

            BaseResponse.Status.EXCEPTION -> {
                state = state.copy(serverError = it.error)
            }

            BaseResponse.Status.ERROR -> {
                state = state.copy(serverError = it.error)
            }

            BaseResponse.Status.TOKEN_EXPIRED -> {
                onEvent(DashEvent.OnLogOut)
            }


            BaseResponse.Status.NO_INTERNET_AVAILABLE -> {
                //currently used local db
            }

            else -> Unit
        }

    }

}