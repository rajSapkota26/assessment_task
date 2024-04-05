package com.example.assessment.uiComponent

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*


@Composable
fun DatePickerUICompo(
    datePicked: String? = "Please select date",
    label: String? = "",
    updatedDate: (date: String?, engDate: String?) -> Unit,
    imageVector: ImageVector,
    paddingTop: Dp = 4.dp,
    paddingBottom: Dp = 8.dp,
    paddingStart: Dp = 4.dp,
    paddingEnd: Dp = 4.dp,
) {
    val context = LocalContext.current
    val showPicker = remember {
        mutableStateOf(false)
    }
    //  val dateConverter = DateConverter()
    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = paddingTop,
                    bottom = paddingBottom,
                    start = paddingStart,
                    end = paddingEnd
                )
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                .clickable {
                    showPicker.value = true
                }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                if (datePicked.isNullOrEmpty()) {
                    Text(
                        text = "$label",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = paddingStart)
                            .weight(8f),
                    )
                } else {
                    Text(
                        text = datePicked,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = paddingStart)
                            .weight(8f),
                    )
                }


                IconButton(
                    onClick = {
                        showPicker.value = true

                    },
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 4.dp)
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "username icon",
                    )
                }
            }

        }

    }

    if (showPicker.value) {
        showCalender(
            context = context,
            updatedDate = { date, engDate ->
                updatedDate(date, engDate)
                showPicker.value = false
            },
        )


    }

}

fun showCalender(
    context: Context,
    updatedDate: (dateYMD: String?, dateDMY: String?) -> Unit,
    minDate: Long = 0
) {
    var mYear = 0
    var mDay = 0
    var mMonth = 0
    val c = Calendar.getInstance()
    mYear = c.get(Calendar.YEAR)
    mMonth = c.get(Calendar.MONTH)
    mDay = c.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, mm, p3 ->
            val day = if (p3 < 10) {
                "0$p3"
            } else {
                p3.toString()
            }

            val month = when (mm) {

                in 0..9 -> {
                    val date = mm + 1
                    "0$date"
                }

                else -> {
                    val date = mm + 1
                    "$date"
                }
            }


            val dates = "$day/$month/$year"
            val apiDate = "$year-$month-$day"
            updatedDate(apiDate, dates)
        },
        mYear,
        mMonth, mDay
    )

    datePickerDialog.show()

    if (minDate > 0) {
        val dates = datePickerDialog.datePicker
        dates.minDate = minDate
    }

}


@Preview(showBackground = true)
@Composable
fun DatePickerUICompoPreview() {
    DatePickerUICompo(
        datePicked = "Date",
        updatedDate = { _, _ -> },
        imageVector = Icons.Filled.DateRange
    )
}
