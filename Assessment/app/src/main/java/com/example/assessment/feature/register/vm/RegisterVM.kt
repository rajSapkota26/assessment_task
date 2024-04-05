package com.example.assessment.feature.register.vm

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment.R
import com.example.assessment.feature.register.dto.User
import com.example.assessment.feature.register.event.RegisterEvent
import com.example.assessment.feature.register.state.RegisterState
import com.example.assessment.service_layer.repository.RoomRepository
import com.example.assessment.utils.generic.BaseResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random


class RegisterVM(var application: Application, private var repository: RoomRepository) :
    ViewModel() {
    var state by mutableStateOf(RegisterState())

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FullNameChange -> {
                state = state.copy(fullName = event.value)
            }

            is RegisterEvent.EmailChange -> {
                state = state.copy(email = event.value)
            }

            is RegisterEvent.PasswordChange -> {
                state = state.copy(password = event.value)
            }

            is RegisterEvent.MobileChange -> {
                state = state.copy(mobile = event.value)
            }

            is RegisterEvent.ConformPasswordChange -> {
                state = state.copy(conformPassword = event.value)
            }

            is RegisterEvent.OnRegister -> {

                requestRegister()
            }

            is RegisterEvent.OnRedirectLogin -> {
                state = state.copy(navigateToLogin = true)
            }

            is RegisterEvent.OnOtpSubmit -> {
                state = state.copy(navigateToLogin = false, navigateToOtp = false, isLoading = true)
                verifyOtpAndUpdateUser(event.value)
            }

            is RegisterEvent.OnOtpDialogClose -> {
                state = state.copy(navigateToOtp = false)
            }

            is RegisterEvent.OnResendOtp -> {
                state = state.copy(navigateToOtp = false, isLoading = true)
                sendNotification()
            }

            is RegisterEvent.OnRedirectOtp -> {
                state = state.copy(navigateToOtp = true)
            }

            is RegisterEvent.AcceptTermsAndCondition -> {
                state = state.copy(isAcceptTermsAndCondition = event.value)
            }

            is RegisterEvent.OnStopErrorDialogue -> {
                state = state.copy(serverError = null)
            }

            else -> {}
        }
    }

    private fun verifyOtpAndUpdateUser(value: String) {
        viewModelScope.launch {
            Timber.v("user submitted otp User ${Gson().toJson(value)}")
            val oldOtp = state.generatedOTP
            if (oldOtp.equals(value)) {
                val user = state.createdUser
                user?.let {
                    val verifiedUser = it.copy(isVerified = true)
                    repository.updateUser(verifiedUser).collect { response ->
                        if (response.status == BaseResponse.Status.SUCCESS) {
                            Timber.v("verified User ${Gson().toJson(response.data)}")
                            state = state.copy(
                                isLoading = false,
                                navigateToOtp = false,
                                onSuccess = true
                            )

                        } else {
                            responseToState(response)
                        }
                    }
                }
            } else {
                state = state.copy(serverError = "invalid Otp")
            }
        }


    }

    private fun requestRegister() {
//        onEvent(RegisterEvent.OnRedirectOtp)
        Timber.v("submitted form Here")
        val email = state.email
        val userName = state.email
        val fullName = state.fullName
        val mobile = state.mobile
        val password = state.password
        val conformPassword = state.conformPassword
        val tAndC = state.isAcceptTermsAndCondition
        if (onValidated(
                fullName = fullName,
                email = email,
                mobile = mobile,
                password = password,
                conformPassword = conformPassword,
                tAndC = tAndC
            )
        ) {
            state = state.copy(isLoading = true)

            val user = User(
                username = userName!!,
                email = email!!,
                mobile = mobile!!,
                fullName = fullName!!,
                password = password!!,
                isVerified = false
            )
            Timber.v("request User ${Gson().toJson(user)}")
            viewModelScope.launch {
                repository.insertUser(user).collect {
                    delay(1000)
                    if (it.status == BaseResponse.Status.SUCCESS) {
                        state = state.copy(isLoading = false)
                        //set user in state
                        //currently we use local db so send otp from local notification
                        // redirect user to verify otp
                        //after verify otp update user in db and set it on shared manager
                        Timber.v("created User ${Gson().toJson(it.data)}")
                        state = state.copy(createdUser = it.data)

                        sendNotification()
                    } else {
                        responseToState(it)
                    }
                }

            }

        }
    }

    private fun <T> responseToState(it: BaseResponse<T>) {
        state = state.copy(isLoading = false)
        when (it.status) {
            BaseResponse.Status.EXCEPTION -> {
                state = state.copy(serverError = it.error)
            }

            BaseResponse.Status.ERROR -> {
                state = state.copy(serverError = it.error)
            }

            else -> Unit
        }

    }

    private fun onValidated(
        fullName: String?,
        email: String?,
        mobile: String?,
        password: String?,
        conformPassword: String?,
        tAndC: Boolean
    ): Boolean {
        if (fullName.isNullOrEmpty()) {
            state = state.copy(fullNameError = "The full name can't be blank")
            return false
        }
        if (fullName.length < 6) {
            state = state.copy(fullNameError = "The full name must have 6 character ")
            return false
        }
        if (email.isNullOrEmpty()) {
            state = state.copy(emailError = "The email can't be blank")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state = state.copy(emailError = "That's not a valid email")
            return false
        }


        if (mobile.isNullOrEmpty()) {
            state = state.copy(mobileError = "The mobile can't be blank")
            return false
        }
        //only for nepali number
        if (mobile.length != 10) {
            state = state.copy(mobileError = "The mobile number must have 10 digit")
            return false
        }
        if (password.isNullOrEmpty()) {
            state = state.copy(passwordError = "The password can't be blank")
            return false
        }
        if (conformPassword.isNullOrEmpty()) {
            state = state.copy(conformPasswordError = "The conform password can't be blank")
            return false
        }
        if (password != conformPassword) {
            state =
                state.copy(conformPasswordError = "The password and conform password does not match")
            return false
        }

        if (!tAndC) {
            state =
                state.copy(isAcceptTermsAndConditionError = "Please Accept Our Terms and Condition")
            return false
        }
        return true
    }

    private fun sendNotification() {
        createNotificationChannel()
        val randomOtp = "${generateRandomNumber()}"
        Timber.v("generated otp User ${Gson().toJson(randomOtp)}")
        state = state.copy(generatedOTP = randomOtp)
        // Build the notification
        val builder = NotificationCompat.Builder(application, "assessment_channel_id")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Otp Code")
            .setContentText("please varify your account with $randomOtp")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Show the notification
        with(NotificationManagerCompat.from(application)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    application,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
        state = state.copy(navigateToOtp = true)

    }

    private fun createNotificationChannel() {
        val name = "I&O Track"
        val descriptionText = "Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("assessment_channel_id", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun generateRandomNumber(): Int {
        val randomNumber = StringBuilder()
        val random = Random(System.currentTimeMillis())

        repeat(6) {
            val digit = random.nextInt(0, 10)
            randomNumber.append(digit)
        }

        return randomNumber.toString().toInt()
    }
}