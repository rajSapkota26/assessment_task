package com.example.assessment.feature.dashboard.state

import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.feature.register.dto.User

data class DashState(
    val allRecords: List<IAndORecord>? = null,

    val totalBalance: String? = null,
    val totalIncome: String? = null,
    val totalExpense: String? = null,

    val serverError: String? = null,
    val isLoading: Boolean = false,
    val onLogoutSuccess: Boolean = false,


    val isTransactionAddAndEditDialogShow: Boolean = false,

    val isTransactionEdit: Boolean = false,
    val editData:IAndORecord?=null,

    val isTransactionDetailShown: Boolean = false,
    val selectedData:IAndORecord?=null,
    val isSettingDialogShown: Boolean = false,
    val session:User?=null

    )
