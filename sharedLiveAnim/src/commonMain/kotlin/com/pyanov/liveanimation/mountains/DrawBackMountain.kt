package com.pyanov.liveanimation.mountains

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawBackMountainLayer(
    canvasWidth: Float,
    canvasHeight: Float,
    color: Color,
    layerHeight: Float,
    startY: Float
) {
    val path = Path().apply {
        moveTo(0f, canvasHeight)
        lineTo(0f, startY + layerHeight)

        val peak1X = canvasWidth * 0.3f
        val peak1Y = startY + layerHeight * 0.4f

        val peak2X = canvasWidth * 0.7f
        val peak2Y = startY + layerHeight * 0.5f

        // Первая гора (левая)
        cubicTo(
            x1 = canvasWidth * 0.15f,
            y1 = startY + layerHeight * 0.9f,
            x2 = canvasWidth * 0.25f,
            y2 = peak1Y,
            x3 = peak1X,
            y3 = peak1Y
        )
        cubicTo(
            x1 = canvasWidth * 0.35f,
            y1 = peak1Y,
            x2 = canvasWidth * 0.45f,
            y2 = startY + layerHeight * 0.7f,
            x3 = canvasWidth * 0.5f,
            y3 = startY + layerHeight * 0.8f
        )

        // Вторая гора (правая)
        cubicTo(
            x1 = canvasWidth * 0.55f,
            y1 = startY + layerHeight * 0.9f,
            x2 = canvasWidth * 0.65f,
            y2 = peak2Y,
            x3 = peak2X,
            y3 = peak2Y
        )
        cubicTo(
            x1 = canvasWidth * 0.75f,
            y1 = peak2Y,
            x2 = canvasWidth * 0.85f,
            y2 = startY + layerHeight * 0.7f,
            x3 = canvasWidth,
            y3 = startY + layerHeight * 0.8f
        )

        lineTo(canvasWidth, canvasHeight)
        close()
    }
    drawPath(
        path = path,
        color = color
    )
} 