package com.example.assessment.feature.dashboard.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.uiComponent.DatePickerUICompo
import com.example.assessment.uiComponent.DropDownMenuCompo
import com.example.assessment.uiComponent.FilledButton
import com.example.assessment.uiComponent.LabelTextCompo
import com.example.assessment.uiComponent.OutlinedTextFieldCompo
import com.example.assessment.utils.dateTime.DateAndTimeUtils

@Composable
fun AddScreen(
    isDialogOpen: Boolean,
    isDataEdit: Boolean,
    onDialogDismiss: () -> Unit,
    onSubmit: (IAndORecord) -> Unit,
    data: IAndORecord?
) {
    val appBarTitle = if (isDataEdit) "Please Modify Record" else "Add New Record"

    val title = remember { mutableStateOf(data?.title ?: "") }
    val titleError = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("${data?.amount ?: ""}") }
    val amountError = remember { mutableStateOf("") }
    val note = remember { mutableStateOf(data?.note ?: "just spend") }
    val noteError = remember { mutableStateOf("") }
    val tag = remember { mutableStateOf(data?.tag ?: "Personal Spending") }
    val tagError = remember { mutableStateOf("") }
    val tDate = remember { mutableStateOf(data?.date ?: DateAndTimeUtils.getTodayDate()) }
    val tDatError = remember { mutableStateOf("") }
    val tType = remember { mutableStateOf(data?.transactionType ?: "Expense") }
    val tTypeError = remember { mutableStateOf("") }
    val tagList = arrayListOf(
        "Housing",
        "Transportation",
        "Food",
        "Utilities",
        "Insurance",
        "Healthcare",
        "Saving & Debts",
        "Personal Spending",
        "Entertainment",
        "Miscellaneous"
    )
    val transactionType = arrayListOf("Income", "Expense")
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
                Column(modifier= Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .imePadding()
                    .verticalScroll(
                        rememberScrollState()
                    )) {
                    LabelTextCompo(title = appBarTitle, fontSize = 24.sp, textAlign = TextAlign.Center)
                    LabelTextCompo(title = "Title",)


                    OutlinedTextFieldCompo(
                        placeholderText = "Type your title",
                        value = title.value,
                        imageVector = Icons.Filled.Tag,
                        onValueChanged = {
                            title.value = it
                        },
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
                        isError = titleError.value.isNotEmpty(),
                        supportingText = titleError.value
                    )
                    LabelTextCompo(title = "Amount",)

                    OutlinedTextFieldCompo(
                        placeholderText = "Type your amount",
                        value = amount.value,
                        imageVector = Icons.Filled.Tag,
                        onValueChanged = {
                            amount.value = it
                        },
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
                        isError = amountError.value.isNotEmpty(),
                        supportingText = amountError.value
                    )
                    LabelTextCompo(title = "Tag",)

                    DropDownMenuCompo(
                        overrideText = tag.value,
                        label = "Pick One",
                        menuItems = tagList,
                        onSelectItem = { _, t ->
                            tag.value = t
                        })
                    if (tagError.value.isNotEmpty()) {
                        ErrorMsg(msg = tagError.value)
                    }
                    LabelTextCompo(title = "Type",)

                    DropDownMenuCompo(
                        overrideText = tType.value,
                        label = "Pick One",
                        menuItems = transactionType,
                        onSelectItem = { _, t -> tType.value = t })
                    if (tTypeError.value.isNotEmpty()) {
                        ErrorMsg(msg = tTypeError.value)

                    }
                    LabelTextCompo(title = "Date",)

                    DatePickerUICompo(
                        datePicked = tDate.value,
                        updatedDate = { _, dat ->
                            if (dat != null) {
                                tDate.value = dat
                            }
                        },
                        imageVector = Icons.Filled.DateRange
                    )
                    if (tDatError.value.isNotEmpty()) {
                        ErrorMsg(msg = tDatError.value)
                    }
                    LabelTextCompo(title = "Note",)

                    OutlinedTextFieldCompo(
                        placeholderText = "Type your note",
                        value = note.value,
                        imageVector = Icons.Filled.Tag,
                        onValueChanged = {
                            note.value = it
                        },
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
                        isError = noteError.value.isNotEmpty(),
                        supportingText = noteError.value
                    )

                    FilledButton("Save Transaction") {
                        if (title.value.isEmpty()) {
                            titleError.value = "title cant be empty"
                        } else if (amount.value.isEmpty()) {
                            amountError.value = "title cant be zero or empty"
                        } else if (tag.value.isEmpty()) {
                            tagError.value = "select tag"
                        } else if (tDate.value.isEmpty()) {
                            tDatError.value = "date cant be empty"
                        } else if (tType.value.isEmpty()) {
                            tTypeError.value = "select type"
                        } else if (note.value.isEmpty()) {
                            noteError.value = "note cant be empty"
                        } else {
                            //handle submit
                            if (isDataEdit) {
                                data?.let {
                                    val editDate = data.copy(
                                        title = title.value,
                                        amount = amount.value.trim().toDouble(),
                                        tag = tag.value,
                                        date = tDate.value,
                                        note = note.value,
                                        transactionType = tType.value
                                    )
                                    onSubmit(editDate)
                                }
                                //modify
                            } else {
//                                create new
                                val addDate = IAndORecord(
                                    title = title.value,
                                    amount = amount.value.trim().toDouble(),
                                    tag = tag.value,
                                    date = tDate.value,
                                    note = note.value,
                                    transactionType = tType.value
                                )
                                onSubmit(addDate)
                            }


                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ErrorMsg(msg: String) {
    Text(
        msg,
        fontSize = 10.sp,
        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
    )
}



