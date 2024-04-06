package com.example.assessment.chart

import androidx.compose.foundation.layout.*
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assessment.ui.theme.AssessmentTheme

@Composable
fun LineChart(
    modifier: Modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
    lineChartData: List<ChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = MaterialTheme.colorScheme.primaryContainer,
    horizontalAxisLabelColor: Color = MaterialTheme.colorScheme.primary,
    horizontalAxisLabelFontSize: TextUnit = 14.sp,
    verticalAxisLabelColor: Color = MaterialTheme.colorScheme.secondary,
    verticalAxisLabelFontSize: TextUnit = 14.sp,
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
    strokeWidth: Dp = 4.dp,
    lineColor: Color = MaterialTheme.colorScheme.secondary,
) {
    val strokeWidthPx = dpToPx(strokeWidth)
    val axisThicknessPx = dpToPx(4.dp)

    Canvas(
        modifier = modifier.aspectRatio(1f)
    ) {

        val bottomAreaHeight = horizontalAxisLabelFontSize.toPx()
        val leftAreaWidth =
            (verticalAxisValues[verticalAxisValues.size - 1].toString().length * verticalAxisLabelFontSize.toPx()
                .div(1.75)).toInt()

        val verticalAxisLength = (size.height - bottomAreaHeight)
        val horizontalAxisLength = size.width - leftAreaWidth

        val distanceBetweenVerticalAxisValues = (verticalAxisLength / (verticalAxisValues.size - 1))

        // Draw horizontal axis
        if (isShowHorizontalLines.not())
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), verticalAxisLength),
                size = Size(horizontalAxisLength, axisThicknessPx)
            )

        // Draw vertical axis
        if (isShowVerticalAxis)
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), 0.0f),
                size = Size(axisThicknessPx, verticalAxisLength)
            )

        // Draw vertical axis values & horizontal lines
        for (index in verticalAxisValues.indices) {

            val x = (leftAreaWidth / 2).toFloat()
            val y = verticalAxisLength - (distanceBetweenVerticalAxisValues).times(index)

            // Draw vertical axis value
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    verticalAxisValues[index].toString(),
                    x,
                    y + verticalAxisLabelFontSize.toPx() / 2,
                    Paint().apply {
                        textSize = verticalAxisLabelFontSize.toPx()
                        color = verticalAxisLabelColor.toArgb()
                        textAlign = Paint.Align.CENTER

                    }
                )
            }

            // Draw horizontal line
            if (isShowHorizontalLines)
                drawRect(
                    color = axisColor,
                    topLeft = Offset(leftAreaWidth.toFloat(), y),
                    size = Size(horizontalAxisLength, axisThicknessPx)
                )
        }

        // Draw lines and it's labels
        val barWidth =
            (drawContext.size.width - leftAreaWidth) / lineChartData.size

        val maxAxisValue = verticalAxisValues[verticalAxisValues.size - 1]

        var previousOffset: Offset? = null

        for (index in lineChartData.indices) {
            val entity = lineChartData[index]

            // Draw line
            val currentOffset = calculateOffset(
                entity.value,
                index,
                maxAxisValue,
                barWidth,
                leftAreaWidth,
                verticalAxisLength
            )

            val end = Offset(currentOffset.x + barWidth.div(2), currentOffset.y)

            drawCircle(
                color = lineColor,
                center = end,
                radius = strokeWidthPx.times(1.5f)
            )

            if (previousOffset != null) {
                val start = Offset(previousOffset.x + barWidth.div(2), previousOffset.y)
                drawLine(
                    start = start,
                    end = end,
                    color = lineColor,
                    strokeWidth = strokeWidthPx
                )
            }

            previousOffset = currentOffset

            // Draw horizontal axis label
            if (lineChartData[index].label?.isNotEmpty() == true) {
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        lineChartData[index].label!!,
                        currentOffset.x + barWidth.div(2),
                        verticalAxisLength + horizontalAxisLabelFontSize.toPx(),
                        Paint().apply {
                            textSize = bottomAreaHeight
                            color = horizontalAxisLabelColor.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}

private fun calculateOffset(
    value: Int,
    index: Int,
    maxAxisValue: Float,
    barWidth: Float,
    leftAreaWidth: Int,
    verticalAxisLength: Float
): Offset {
    var x = barWidth * index
    x += leftAreaWidth

    val barHeightPercentage = (value / maxAxisValue)
    val barHeightInPixel = barHeightPercentage * verticalAxisLength
    val y = verticalAxisLength - barHeightInPixel

    return Offset(x, y)
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AssessmentTheme {
        val lineChartData = ArrayList<ChartEntity>()
        lineChartData.add(ChartEntity(150, "1"))
        lineChartData.add(ChartEntity(250, "2"))
        lineChartData.add(ChartEntity(350, "3"))
        lineChartData.add(ChartEntity(450, "4"))
        lineChartData.add(ChartEntity(550, "5"))

        LineChart(
            lineChartData = lineChartData,
            verticalAxisValues = listOf(0.0f, 100.0f, 200.0f, 300.0f, 400.0f, 500.0f),
        )
    }
}




@Composable
fun IncomeExpenseGraph(
    income: Float,
    expense: Float,
    modifier: Modifier = Modifier,
    graphColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary
) {
    val total = income + expense
    val incomeAngle = 360 * (income / total)
    val expenseAngle = 360 * (expense / total)

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 - 16 // Adjust padding here

            // Draw background circle
            drawCircle(color = backgroundColor, radius = radius)

            // Draw income arc
            drawArc(
                color = graphColor,
                startAngle = -90f,
                sweepAngle = incomeAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 8.dp.toPx())
            )

            // Draw expense arc
            drawArc(
                color = Color(0xFF26C7DB), // You can change color for expenses
                startAngle = incomeAngle - 90f,
                sweepAngle = expenseAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 8.dp.toPx())
            )
        }
    }
}
@Composable
fun IncomeExpenseScreen() {
    Column {
        IncomeExpenseGraph(
            income = 1500f,
            expense = 1000f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewIncomeExpenseScreen() {
    AssessmentTheme {
        IncomeExpenseScreen()
    }
}