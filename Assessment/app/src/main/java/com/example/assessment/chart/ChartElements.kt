package com.example.assessment.chart

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChartElements(
    val showVerticalLine: Boolean = false,
    val showGridLines: Boolean = true,
    val showHorizontalLabels: Boolean = true,
    val showLegend: Boolean = true,
    val gridLinesColor: Color = Color(0xFF006590),
    val labelColor: Color = Color(0xFF001E2F),
    val barWidth: Dp = 14.dp,
    val legendWidth: Dp = 1.dp,
    val fontSize: TextUnit = 14.sp,
)
