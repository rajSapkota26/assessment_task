package com.example.assessment.feature.login.vm

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment.feature.login.event.LoginEvent
import com.example.assessment.feature.login.state.LoginState
import com.example.assessment.feature.register.dto.User
import com.example.assessment.service_layer.repository.RoomRepository
import com.example.assessment.utils.generic.BaseResponse
import com.example.assessment.utils.session.SessionManager
import kotlinx.coroutines.launch
import timber.log.Timber


class LoginVM(private var application: Application, private var repository: RoomRepository) :
    ViewModel() {

    var state by mutableStateOf(LoginState())

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PasswordChange -> {
                state = state.copy(password = event.password)
            }

            is LoginEvent.EmailChange -> {
                state = state.copy(email = event.email)
            }

            is LoginEvent.OnLogin -> {
                onSubmit()
            }

            is LoginEvent.OnStopErrorDialogue -> {
                state = state.copy(serverError = null)
            }

            else -> {}
        }
    }

    private fun onSubmit() {
        val email = state.email
        val password = state.password
        if (onValidated(email, password)) {
            //server call
            state = state.copy(isLoading = true)
            viewModelScope.launch {
                repository.getUserByUserNameAndPassword(email!!, password!!).collect {
                    if (it.status == BaseResponse.Status.SUCCESS) {
                        state = state.copy(isLoading = false)
                        saveToSharedPref(it.data)
                        //after verify otp update user in db and set it on shared manager
                    } else {
                        responseToState(it)
                    }
                }


            }

        }
    }


    private fun saveToSharedPref(data: User?) {
        val sharedPreferencesManager = SessionManager(application)
        data?.let {
            state = if (sharedPreferencesManager.saveSession(it)) {
                state.copy(onSuccess = true)
            } else {
                state.copy(serverError = "Internal Server Error")
            }


        }
    }

    private fun getSession() {
        try {
            val sharedPreferencesManager = SessionManager(application)
            val data = sharedPreferencesManager.getSession()
            if (data != null) {
                if (data.isVerified) {
                    state = state.copy(isSessionActive = true)

                }
            } else {
                state = state.copy(isSessionActive = false)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun onValidated(email: String?, password: String?): Boolean {
        if (email.isNullOrEmpty()) {
            state = state.copy(emailError = "The email can't be blank")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state = state.copy(emailError = "That's not a valid email")
            return false
        }
        if (password.isNullOrEmpty()) {
            state = state.copy(passwordError = "The password can't be blank")
            return false
        }
        return true
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
                //in this section we create user so no need to handle it
            }


            BaseResponse.Status.NO_INTERNET_AVAILABLE -> {
                //currently used local db
            }

            else -> Unit
        }

    }

    init {
        getSession()
    }
}