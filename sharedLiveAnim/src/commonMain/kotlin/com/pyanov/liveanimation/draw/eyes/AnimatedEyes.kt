package com.pyanov.liveanimation.draw.eyes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Рисует два глаза с зрачками. Размеры и позиции глаз задаются параметрами.
 * Радиус зрачка всегда равен 0.5 от радиуса глаза.
 * Расстояние между центрами глаз всегда равно 0.8 от диаметра глаза.
 * Смещение зрачков (pupilOffset) передаётся снаружи и может быть анимировано.
 * Размер Canvas вычисляется автоматически.
 *
 * @param eyesCenter Центр между глазами (Offset, px)
 * @param eyeRadius Радиус глаз (px)
 * @param pupilOffset Смещение зрачков относительно центра глаз (Offset, px)
 */
@Composable
fun AnimatedEyes(
    eyesCenter: Offset,
    eyeRadius: Float,
    pupilOffset: Offset = Offset.Zero,
) {
    val pupilRadius = eyeRadius * 0.5f
    val eyesDistance = eyeRadius * 2f * 0.8f
    val maxPupilOffset = pupilRadius
    val padding = 4.dp
    val canvasWidthDp = ((eyesDistance + 2 * eyeRadius + 2 * maxPupilOffset) / 2f).dp + padding * 2
    val canvasHeightDp = ((2 * eyeRadius + 2 * maxPupilOffset) / 2f).dp + padding * 2
    val leftEyeCenter = Offset(eyesCenter.x - eyesDistance / 2f, eyesCenter.y)
    val rightEyeCenter = Offset(eyesCenter.x + eyesDistance / 2f, eyesCenter.y)
    Canvas(
        modifier = Modifier
            .size(canvasWidthDp, canvasHeightDp)
            .clipToBounds()
    ) {
        // Глаза
        drawCircle(
            color = Color.White,
            radius = eyeRadius,
            center = leftEyeCenter
        )
        drawCircle(
            color = Color.White,
            radius = eyeRadius,
            center = rightEyeCenter
        )
        // Зрачки
        drawCircle(
            color = Color.Black,
            radius = pupilRadius,
            center = leftEyeCenter + pupilOffset
        )
        drawCircle(
            color = Color.Black,
            radius = pupilRadius,
            center = rightEyeCenter + pupilOffset
        )
    }
} 