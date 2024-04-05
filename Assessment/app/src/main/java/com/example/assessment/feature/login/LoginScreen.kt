package com.example.assessment.feature.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.assessment.R
import com.example.assessment.feature.login.event.LoginEvent
import com.example.assessment.feature.login.state.LoginState
import com.example.assessment.uiComponent.CommonCard
import com.example.assessment.uiComponent.DialogMessage
import com.example.assessment.uiComponent.FilledButton
import com.example.assessment.uiComponent.FilledTButton
import com.example.assessment.uiComponent.LabelTextCompo
import com.example.assessment.uiComponent.OutlinedTextFieldCompo
import com.example.assessment.uiComponent.PasswordCompo
import com.example.assessment.utils.navigation.NavigationScreen
import com.example.samplesetting.component.ProgressScreen
import timber.log.Timber

@Composable
fun LoginScreen(
    navController: NavHostController,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
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
    uiState: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    Scaffold(
    ) { pad ->
        val a = pad
        CommonCard {
            Column(modifier = Modifier.padding(8.dp)) {
                LoginContent(
                    state = uiState,
                    onEvent = {
                        onEvent(it)
                    },
                    navController = navController
                )

            }
        }
    }
}

@Composable
fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    navController: NavHostController
) {
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current
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
        Spacer(modifier = Modifier.height(56.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
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
            LabelTextCompo(title = "Welcome,\nProceed to login", fontSize = 24.sp)

        }

        LabelTextCompo(title = "Email")
        OutlinedTextFieldCompo(
            placeholderText = "Type your email",
            value = state.email ?: "",
            imageVector = Icons.Filled.Email,
            onValueChanged = {
                onEvent(LoginEvent.EmailChange(it))
            },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
            isError = state.emailError != null,
            supportingText = state.emailError ?: ""
        )
        LabelTextCompo(title = "Password")

        PasswordCompo(
            password = state.password ?: "",
            onPasswordChanged = {
                onEvent(LoginEvent.PasswordChange(it))
            },
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
            isError = state.passwordError != null,
            supportingText = state.passwordError ?: ""
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        FilledButton(
            label = "Login",
        ) {
            onEvent(LoginEvent.OnLogin)
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        FilledTButton("Create Account") {
            navController.navigate(NavigationScreen.registerScreen)
//            onEvent(LoginEvent.OnRedirectRegister)
        }

    }

}

@Composable
private fun ManageScreenState(
    navController: NavHostController,
    context: Context,
    uiState: LoginState,
    onEvent: (LoginEvent) -> Unit
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
                onEvent(LoginEvent.OnStopErrorDialogue)
            },
            onClickCancel = {
                onEvent(LoginEvent.OnStopErrorDialogue)
            },
        )
    }
    if (uiState.onSuccess) {
        navController.popBackStack()
        navController.navigate(NavigationScreen.homeScreen)
        navController.clearBackStack(NavigationScreen.loginScreen)

    }
    if (uiState.isSessionActive) {
        navController.popBackStack()
        navController.navigate(NavigationScreen.homeScreen)
        navController.clearBackStack(NavigationScreen.loginScreen)

    }


}

@Composable
private fun InitDependency(
    navController: NavHostController,
    context: Context,
    uiState: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
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
