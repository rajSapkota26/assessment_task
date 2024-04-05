package com.example.assessment.feature.dashboard.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.uiComponent.FilledButton

@Composable
fun TransactionDetail(
    isDialogOpen: Boolean,
    onDialogDismiss: () -> Unit,
    onDelete: (IAndORecord) -> Unit,
    onEdit: (IAndORecord) -> Unit,
    data: IAndORecord
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
                    Text(
                        "Transactions Detail",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Start
                    )
                    Column( modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        TitleSubTitle("Title", data.title)
                        TitleSubTitle("Amount", "${data.amount}")
                        TitleSubTitle("Tag", data.tag)
                        TitleSubTitle("Type", data.transactionType)
                        TitleSubTitle("Note", data.note)
                        TitleSubTitle("Created At", data.createdAt)
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.fillMaxWidth(0.48f)) {
                                FilledButton("Delete") {
                                    onDelete(data)
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(modifier = Modifier.fillMaxWidth(1f)) {
                                FilledButton("Edit") {
                                    onEdit(data)
                                }
                            }
                        }

                    }

                }

            }
        }
    }
}

@Composable
fun TitleSubTitle(title: String, subTitle: String) {
    Text(
        title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        subTitle,
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(4.dp))
}