package com.pyanov.liveanimation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.sin

fun DrawScope.drawFrontMountainLayer(
    canvasWidth: Float,
    canvasHeight: Float,
    color: Color,
    layerHeight: Float,
    startY: Float
) {
    val path = Path().apply {
        moveTo(0f, canvasHeight)
        lineTo(0f, startY + layerHeight)

        val peakX = canvasWidth * 0.3f // Смещаем пик влево
        val peakY = startY + layerHeight * 0.4f

        cubicTo(
            x1 = canvasWidth * 0.05f,
            y1 = startY + layerHeight * 0.7f,
            x2 = canvasWidth * 0.15f,
            y2 = peakY,
            x3 = peakX,
            y3 = peakY
        )
        cubicTo(
            x1 = canvasWidth * 0.4f,
            y1 = peakY,
            x2 = canvasWidth * 0.6f,
            y2 = startY + layerHeight * 0.9f,
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