package com.example.assessment.feature.dashboard.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.assessment.feature.register.dto.User
import com.example.assessment.uiComponent.FilledButton
import com.example.assessment.uiComponent.LabelTextCompo

@Composable
fun SettingScreen(
    isDialogOpen: Boolean,
    onDialogDismiss: () -> Unit,
    onDelete: () -> Unit,
    onLogout: () -> Unit,
    session: User?,
) {
    if (isDialogOpen) {
        Dialog(properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        ), onDismissRequest = {
            onDialogDismiss()
        }) {
            val localFocusManager = LocalFocusManager.current
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            localFocusManager.clearFocus(true)
                        })
                    }, shape = RoundedCornerShape(5.dp), color = Color.White
            ) {
                Column(modifier = Modifier.padding(8.dp)) {

                    LabelTextCompo(title = "Profile", fontSize = 18.sp)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .size(75.dp)
                                .align(Alignment.Center)
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary

                        )
                    }

                    LabelTextCompo(title = "FullName:",
                    subTitle = "${session?.fullName}")
                    LabelTextCompo(title = "Email:",
                        subTitle = "${session?.email}")
                    LabelTextCompo(title = "Mobile:",
                        subTitle = "${session?.mobile}")


                    FilledButton("Delete Account") {
                        onDelete()
                    }
                    FilledButton("Log out") {
                        onLogout()
                    }

                }

            }
        }
    }
}