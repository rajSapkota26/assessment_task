package com.example.assessment.feature.register.screen.otp

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.assessment.R
import com.example.assessment.ui.theme.grey
import com.example.assessment.uiComponent.FilledButton
import com.example.assessment.uiComponent.FilledTButton
import java.text.DecimalFormat
import java.text.NumberFormat



@Composable
fun VerificationDialog(
    isDialogOpen: Boolean,
    onVerify: (String) -> Unit,
    onResendOTP:() -> Unit,
    onDialogDismiss : () -> Unit,
) {

    val verificationCode = remember { mutableStateOf("") }

    val millinInFuture = remember {
        mutableStateOf(120000L) // 2 min at first
    }

    val resendCount = remember {
        mutableStateOf(1L)
    }

    val isResendOtpEnable = remember {
        mutableStateOf(false)
    }

    if (isDialogOpen) {

        var timerText by remember {
            mutableStateOf("")
        }

        LaunchedEffect(Unit) {
            StartTimer(
                millinInFuture.value.times(resendCount.value),
                isResendOtpEnable = isResendOtpEnable
            ) { time ->
                timerText = time
            }
        }

        Dialog(properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        ),
            onDismissRequest = {
                onDialogDismiss()
            }) {
            val localFocusManager = LocalFocusManager.current
            Surface(
                modifier = Modifier
                    .fillMaxSize().pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            localFocusManager.clearFocus(true)
                        })
                    },
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                   ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    "Verify!",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                )
                Text(
                    text = "Please verify OTP",
                    fontWeight = FontWeight.Light,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = verificationCode.value,
                    onValueChange = {
                        if (it.length <= 6) {
                            verificationCode.value = it
                        }
                    },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    decorationBox = {
                        Row(horizontalArrangement = Arrangement.SpaceAround) {
                            repeat(6) { index ->
                                CharView(
                                    index = index,
                                    text = verificationCode.value,
                                    charSize = 20.sp,
                                    containerSize = 25.dp * 2,
                                    charBackground = grey,
                                )
                            }
                        }
                    },
                )


                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                FilledButton("Submit") {
                    onVerify(verificationCode.value)

                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                //TODO TESTING REMAINING FOR OTP RESEND....
                if (isResendOtpEnable.value) {
                    FilledTButton("Resend") {
                        resendCount.value = resendCount.value.plus(1L)
                        onResendOTP()
                        StartTimer(
                            resendCount.value.times(millinInFuture.value),
                            isResendOtpEnable = isResendOtpEnable
                        ) { time ->
                            timerText = time
                        }

                    }
                } else {
                    Row() {
                        Text(
                            "Resend OTP code in ",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                        Text(
                            timerText,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))

            }
        }
        }
    }
}


private fun StartTimer(
    millinInFuture: Long,
    isResendOtpEnable: MutableState<Boolean>,
    time: (String) -> Unit
): CountDownTimer {
    isResendOtpEnable.value = false
    return object : CountDownTimer(millinInFuture, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            // Used for formatting digit to be in 2 digits only
            val f: NumberFormat = DecimalFormat("00")
            val hour = millisUntilFinished / 3600000 % 24
            val min = millisUntilFinished / 60000 % 60
            val sec = millisUntilFinished / 1000 % 60
//            time(f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec))
            time( f.format(min).toString() + ":" + f.format(sec))
        }

        // When the task is over it will print 00:00:00 there
        override fun onFinish() {
            time("00:00:00")
            isResendOtpEnable.value = true  // enable resend otp
        }
    }.start()
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    charSize: TextUnit,
    containerSize: Dp,
    charBackground: Color,
) {
    val modifier = Modifier
        .size(containerSize)
        .clip(RoundedCornerShape(8.dp))
        .background(charBackground)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val char = when {
            index >= text.length -> ""
            else -> text[index].toString()
        }
        Text(
            text = char,
            color = Color.Black,
            modifier = modifier.wrapContentHeight(),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = charSize,
            textAlign = TextAlign.Center,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerification(){
    VerificationDialog(
        isDialogOpen = true,
        onVerify = {},
        onResendOTP = { /*TODO*/ },
    onDialogDismiss = {})
}