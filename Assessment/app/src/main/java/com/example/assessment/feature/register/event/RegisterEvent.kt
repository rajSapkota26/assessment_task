package com.example.assessment.feature.register.event


sealed class RegisterEvent {

    data class FullNameChange(val value: String) : RegisterEvent()
    data class MobileChange(val value: String) : RegisterEvent()
    data class ConformPasswordChange(val value: String) : RegisterEvent()
    data class PasswordChange(val value: String) : RegisterEvent()
    data class EmailChange(val value: String) : RegisterEvent()
    data class AcceptTermsAndCondition(val value: Boolean) : RegisterEvent()


    data object OnRegister : RegisterEvent()

    data object OnRedirectLogin : RegisterEvent()
    data object OnRedirectOtp : RegisterEvent()
    data class OnOtpSubmit(val value:String) : RegisterEvent()
    data object OnOtpDialogClose : RegisterEvent()
    data object OnResendOtp : RegisterEvent()

    data object OnStopErrorDialogue : RegisterEvent()
}