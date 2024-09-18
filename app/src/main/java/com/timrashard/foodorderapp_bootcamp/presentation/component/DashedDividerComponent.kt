package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DashedDividerComponent(
    modifier: Modifier = Modifier,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 4.dp,
    color: Color = Color.Gray
) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val dashLengthPx = dashLength.toPx()
        val gapLengthPx = gapLength.toPx()

        val totalDashLength = dashLengthPx + gapLengthPx
        var currentOffset = 0f

        while (currentOffset < canvasWidth) {
            drawLine(
                color = color,
                start = Offset(currentOffset, canvasHeight / 2),
                end = Offset(currentOffset + dashLengthPx, canvasHeight / 2),
                strokeWidth = canvasHeight // Height determines line thickness
            )
            currentOffset += totalDashLength
        }
    }
}
