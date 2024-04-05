package com.example.assessment.feature.login.event

sealed class LoginEvent {
    data class PasswordChange(val password: String) : LoginEvent()
    data class EmailChange(val email: String) : LoginEvent()
    data object OnLogin : LoginEvent()
    data object OnStopErrorDialogue : LoginEvent()
}