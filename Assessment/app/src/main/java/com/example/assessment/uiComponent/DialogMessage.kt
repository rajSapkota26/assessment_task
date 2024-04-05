package com.example.assessment.uiComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.assessment.R


@Composable
fun DialogMessage(
    isDialogMessageOpen: Boolean,
    message: String = "",
    btnOkTitle: String = "Ok",
    onClickOK: () -> Unit,
    showOkButton: Boolean = true,
    onClickCancel: () -> Unit,
    dismissOnBackPress: Boolean = true,
) {

    if (isDialogMessageOpen) {
        Dialog(properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = false
        ),
            onDismissRequest = {
                onClickCancel()
            }) {
            Surface(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .clickable { },
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    CommonCard(
                        modifier = Modifier,
                    ) {
                        Box(
                            modifier = Modifier
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                                Image(
                                    painter = painterResource(R.drawable.logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    contentScale = ContentScale.FillBounds
                                )

                                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                                Text(
                                    text = "Message",
                                    modifier = Modifier.wrapContentSize(),
                                    textAlign = TextAlign.Center,
//                                    color = colorResource(id = R.color.text_color_red),
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )


                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Text(
                                    text = message,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.padding(vertical = 4.dp))


                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth(1f)
                                ) {
                                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                    Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                                        FilledButton(
                                            label = "$btnOkTitle",
                                        ) {
                                            onClickOK()
                                        }
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                }

                            }
                        }
                    }

                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DialogMessagePreview() {
    Column() {
        DialogMessage(
            isDialogMessageOpen = true,
            message = "message goes here",
            onClickOK = {},
            onClickCancel = {}
        )

    }
}
