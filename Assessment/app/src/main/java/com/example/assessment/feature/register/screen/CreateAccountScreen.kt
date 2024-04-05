package com.example.assessment.feature.register.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.assessment.R
import com.example.assessment.feature.register.screen.otp.VerificationDialog
import com.example.assessment.feature.register.event.RegisterEvent
import com.example.assessment.feature.register.state.RegisterState
import com.example.assessment.uiComponent.CheckBoxCompo
import com.example.assessment.uiComponent.CommonCard
import com.example.assessment.uiComponent.DialogMessage
import com.example.assessment.uiComponent.FilledButton
import com.example.assessment.uiComponent.LabelTextCompo
import com.example.assessment.uiComponent.OutlinedTextFieldCompo
import com.example.assessment.uiComponent.PasswordCompo
import com.example.assessment.utils.navigation.NavigationScreen
import com.example.samplesetting.component.ProgressScreen
import timber.log.Timber

@Composable
fun CreateAccountScreen(
    navController: NavHostController,
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    val context = LocalContext.current

    InitDependency(
        context = context,
        uiState = state,
        navController = navController
    ) { onEvent(it) }
    ManageScreenState(
        context = context,
        uiState = state,
        navController = navController
    ) { onEvent(it) }
    MainContent(
        context = context,
        uiState = state,
        navController = navController
    ) { onEvent(it) }
}

@Composable
private fun MainContent(
    navController: NavHostController,
    context: Context,
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    Scaffold(
    ) { pad ->
        val a = pad
        CommonCard {
            Column(modifier = Modifier.padding(8.dp)) {
                RegistrationContent(
                    state = uiState,
                    onEvent = {
                        onEvent(it)
                    },
                    context = context,
                    navController = navController
                )

            }
        }
    }
}

@Composable
fun RegistrationContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    context: Context,
    navController: NavHostController
) {
    val localFocusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus(true)
                })
            }
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable {
                    navController.navigate(NavigationScreen.homeScreen)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)

            )
            LabelTextCompo(title = "Lets Create a Account", fontSize = 24.sp)

        }
        LabelTextCompo(title = "Full Name")
        OutlinedTextFieldCompo(
            placeholderText = "Type your full name",
            value = state.fullName ?: "",
            imageVector = Icons.Filled.Person,
            onValueChanged = {
                onEvent(RegisterEvent.FullNameChange(it))
            },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { localFocusManager.moveFocus(FocusDirection.Down) }),
            isError = state.fullNameError != null,
            supportingText = state.fullNameError ?: ""
        )
        LabelTextCompo(title = "Email")

        OutlinedTextFieldCompo(
            placeholderText = "Type your email",
            value = state.email ?: "",
            imageVector = Icons.Filled.Email,
            onValueChanged = {
                onEvent(RegisterEvent.EmailChange(it))
            },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { localFocusManager.moveFocus(FocusDirection.Down) }),
            isError = state.emailError != null,
            supportingText = state.emailError ?: ""
        )
        LabelTextCompo(title = "Phone")


        OutlinedTextFieldCompo(
            placeholderText = "Type your mobile number",
            value = state.mobile ?: "",
            imageVector = Icons.Filled.PhoneAndroid,
            onValueChanged = {
                onEvent(RegisterEvent.MobileChange(it))
            },
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { localFocusManager.moveFocus(FocusDirection.Down) }),
            isError = state.mobileError != null,
            supportingText = state.mobileError ?: ""
        )
        LabelTextCompo(title = "Password")

        PasswordCompo(
            password = state.password ?: "",
            onPasswordChanged = {
                onEvent(RegisterEvent.PasswordChange(it))
            },
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
            isError = state.passwordError != null,
            supportingText = state.passwordError ?: ""
        )
        LabelTextCompo(title = "Conform Password")

        PasswordCompo(
            password = state.conformPassword ?: "",
            onPasswordChanged = {
                onEvent(RegisterEvent.ConformPasswordChange(it))
            },
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { localFocusManager.clearFocus() }),
            isError = state.conformPasswordError != null,
            supportingText = state.conformPasswordError ?: "",
            placeholderText = "conform Password"
        )
        CheckBoxCompo(
            isChecked = state.isAcceptTermsAndCondition,
            label = "Accept our Trems And Condition",
            onCheck = {
                Timber.v(it.toString())
                onEvent(RegisterEvent.AcceptTermsAndCondition(it))
            }
        )
        if (state.isAcceptTermsAndConditionError?.isNotEmpty() == true) {
            Text(
                "Please Accept our terms and condition",
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
        FilledButton("Submit") {
            onEvent(RegisterEvent.OnRegister)
        }

    }
}

@Composable
private fun ManageScreenState(
    navController: NavHostController,
    context: Context,
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    if (uiState.isLoading) {
        ProgressScreen(
            navHostController = navController,
            message = "Sending Request"
        )
    }
    if (uiState.serverError != null) {
        val errorMessage = uiState.serverError
        Timber.v("show error message : $errorMessage")
        DialogMessage(
            isDialogMessageOpen = true,
            message = "$errorMessage",
            onClickOK = {
                onEvent(RegisterEvent.OnStopErrorDialogue)
            },
            onClickCancel = {},
        )
    }
    if (uiState.navigateToOtp) {
        VerificationDialog(
            isDialogOpen = true,
            onVerify = {
                onEvent(RegisterEvent.OnOtpSubmit(it))
            },
            onResendOTP = {
                onEvent(RegisterEvent.OnResendOtp)
            },
            onDialogDismiss = {
                onEvent(RegisterEvent.OnOtpDialogClose)
            })
    }
    if (uiState.onSuccess
    ) {

        navController.popBackStack()
        navController.navigate(NavigationScreen.loginScreen)
        navController.clearBackStack(NavigationScreen.registerScreen)
    }


}

@Composable
private fun InitDependency(
    navController: NavHostController,
    context: Context,
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                (context as Activity).requestPermissions(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    22
                )
            }
        }

    }

}
//fun Context.getActivity(): ComponentActivity? = when (this) {
//    is ComponentActivity -> this
//    is ContextWrapper -> baseContext.getActivity()
//    else -> null
//}