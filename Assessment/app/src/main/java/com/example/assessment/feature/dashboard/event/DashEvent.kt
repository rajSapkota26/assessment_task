package com.example.assessment.feature.dashboard.event

import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.feature.login.event.LoginEvent

sealed class DashEvent {

    data class OnTransactionAddDialogOpen(val isEdit:Boolean,var value: IAndORecord?=null) : DashEvent()
    data object OnTransactionAddDialogCLose : DashEvent()

//    data object OnTransactionEditDialogOpen : DashEvent()
//    data object OnTransactionEditDialogClose : DashEvent()

    data class OnTransactionDetailDialogOpen(var value: IAndORecord) : DashEvent()
    data object OnTransactionDetailDialogClose : DashEvent()

    data class OnTransactionDetailDelete(var value: IAndORecord) : DashEvent()

    data class OnTransactionDetailAdd(var value: IAndORecord,val isEdit:Boolean,) : DashEvent()

    data object OnSettingDialogOpen : DashEvent()
    data object OnSettingDialogClose : DashEvent()
    data object OnStopErrorDialogue : DashEvent()

    data object OnLogOut : DashEvent()
    data object OnDeleteAccount : DashEvent()

    data object OnInit : DashEvent()


}