package com.example.assessment.feature.register.state

import com.example.assessment.feature.register.dto.User


data class RegisterState(
    val fullName :String?="",
    val email:String?="",
    val mobile:String?="",
    val password :String?="",
    val conformPassword:String?="",

    val fullNameError :String?=null,
    val emailError:String?=null,
    val mobileError:String?=null,
    val passwordError:String?=null,
    val conformPasswordError:String?=null,

    val isAcceptTermsAndCondition:Boolean=false,
    val isAcceptTermsAndConditionError:String?="",

    val serverError:String?=null,
    val isLoading:Boolean=false,
    val onSuccess:Boolean=false,

    val navigateToLogin:Boolean=false,

    val navigateToOtp:Boolean=false,
    val createdUser: User?=null,
    val generatedOTP:String?="",
)