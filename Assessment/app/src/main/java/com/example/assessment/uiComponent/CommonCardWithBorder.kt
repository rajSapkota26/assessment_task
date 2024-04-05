package com.example.assessment.uiComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonCard(
    modifier: Modifier = Modifier,
    color: Color= MaterialTheme.colorScheme.surface,
    paddingTop: Dp = 8.dp,
    paddingBottom: Dp = 8.dp,
    paddingStart: Dp = 8.dp,
    paddingEnd: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(
                top = paddingTop,
                bottom = paddingBottom,
                start = paddingStart,
                end = paddingEnd
            )
            .background(color)
    ) {
        content.invoke()
    }
}