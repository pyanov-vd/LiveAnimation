package com.pyanov.liveanimation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.min

fun DrawScope.drawMountain(
    width: Float,
    height: Float,
    mountainWidth: Float,
    mountainHeight: Float,
    startX: Float,
    startY: Float
) {
    val path = Path().apply {
        // Начинаем с левого нижнего угла горы
        moveTo(startX, startY + mountainHeight)

        // Рисуем левую часть горы до пика
        quadraticTo(
            x1 = startX + mountainWidth * 0.3f,
            y1 = startY + mountainHeight * 0.6f,
            x2 = startX + mountainWidth * 0.45f,
            y2 = startY + mountainHeight * 0.35f
        )

        // Рисуем скругленный пик
        quadraticTo(
            x1 = startX + mountainWidth * 0.5f,
            y1 = startY + mountainHeight * 0.3f,
            x2 = startX + mountainWidth * 0.55f,
            y2 = startY + mountainHeight * 0.35f
        )

        // Рисуем правую часть горы
        quadraticTo(
            x1 = startX + mountainWidth * 0.7f,
            y1 = startY + mountainHeight * 0.6f,
            x2 = startX + mountainWidth,
            y2 = startY + mountainHeight
        )

        // Замыкаем путь
        close()
    }

    // Рисуем гору серым цветом
    drawPath(
        path = path,
        color = Color.Gray,
        style = Stroke(width = 2f)
    )

    // Заполняем гору более светлым серым цветом
    drawPath(
        path = path,
        color = Color.LightGray.copy(alpha = 0.7f)
    )
}

fun DrawScope.drawEyes(
    mountainWidth: Float,
    mountainHeight: Float,
    startX: Float,
    startY: Float,
    pupilOffsetY: Float
) {
    val eyeRadius = min(mountainWidth, mountainHeight) * 0.15f
    val pupilRadius = eyeRadius * 0.5f

    // Левый глаз
    drawCircle(
        color = Color.White,
        radius = eyeRadius,
        center = Offset(
            x = startX + mountainWidth * 0.35f,
            y = startY + mountainHeight * 0.65f
        )
    )

    // Правый глаз
    drawCircle(
        color = Color.White,
        radius = eyeRadius,
        center = Offset(
            x = startX + mountainWidth * 0.65f,
            y = startY + mountainHeight * 0.65f
        )
    )

    // Левый зрачок
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = Offset(
            x = startX + mountainWidth * 0.35f,
            y = startY + mountainHeight * 0.65f + pupilOffsetY
        )
    )

    // Правый зрачок
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = Offset(
            x = startX + mountainWidth * 0.65f,
            y = startY + mountainHeight * 0.65f + pupilOffsetY
        )
    )
}