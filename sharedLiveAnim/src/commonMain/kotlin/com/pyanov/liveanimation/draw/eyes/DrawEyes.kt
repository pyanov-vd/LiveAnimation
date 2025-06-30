package com.pyanov.liveanimation.draw.eyes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawEyes(
    eyeCenterX: Float,
    eyeCenterY: Float,
    eyeSize: Float,
    pupilOffsetY: Float
) {
    val eyeRadius = eyeSize * 0.5f
    val pupilRadius = eyeRadius * 0.5f

    // Левый глаз
    drawCircle(
        color = Color.White,
        radius = eyeRadius,
        center = Offset(
            x = eyeCenterX - eyeSize * 0.45f,
            y = eyeCenterY
        )
    )

    // Правый глаз
    drawCircle(
        color = Color.White,
        radius = eyeRadius,
        center = Offset(
            x = eyeCenterX + eyeSize * 0.45f,
            y = eyeCenterY
        )
    )

    // Левый зрачок
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = Offset(
            x = eyeCenterX - eyeSize * 0.45f,
            y = eyeCenterY + pupilOffsetY
        )
    )

    // Правый зрачок
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = Offset(
            x = eyeCenterX + eyeSize * 0.45f,
            y = eyeCenterY + pupilOffsetY
        )
    )
}