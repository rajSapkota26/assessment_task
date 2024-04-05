package com.example.assessment.feature.login.state

data class  LoginState(
    val password :String?="",
    val email:String?="",
    val passwordError :String?=null,
    val emailError:String?=null,
    val serverError:String?=null,

    val isLoading:Boolean=false,
    val onSuccess:Boolean=false,
    val isSessionActive:Boolean=false,
)
