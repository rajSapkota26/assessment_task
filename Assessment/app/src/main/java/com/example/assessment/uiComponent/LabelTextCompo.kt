package com.example.assessment.uiComponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelTextCompo(
    title:String,
    fontSize:TextUnit = 16.sp,
    textAlign: TextAlign?= TextAlign.Start
){
    Text(
        title,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
        textAlign=textAlign
    )
}
@Composable
fun LabelTextCompo(
    title:String,
    subTitle:String,
    fontSize:TextUnit = 16.sp,
    textAlign: TextAlign?= TextAlign.Start
){
    Row {
        Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            textAlign=textAlign
        )
        Text(
            subTitle,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            textAlign=textAlign
        )
    }

}