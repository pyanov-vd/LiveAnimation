package com.pyanov.liveanimation.draw.mountains

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawMountainLayer(
    canvasWidth: Float,
    canvasHeight: Float,
    color: Color,
    layerHeight: Float,
    startY: Float,
    mountains: List<Mountain>
) {
    val path = Path().apply {
        moveTo(0f, canvasHeight)
        lineTo(0f, startY + layerHeight)
        for (mountain in mountains) {
            for (curve in mountain.mountains) {
                cubicTo(
                    x1 = canvasWidth * curve.x1,
                    y1 = startY + layerHeight * curve.y1,
                    x2 = canvasWidth * curve.x2,
                    y2 = startY + layerHeight * curve.y2,
                    x3 = canvasWidth * curve.x3,
                    y3 = startY + layerHeight * curve.y3
                )
            }
        }
        lineTo(canvasWidth, canvasHeight)
        close()
    }
    drawPath(
        path = path,
        color = color
    )
}