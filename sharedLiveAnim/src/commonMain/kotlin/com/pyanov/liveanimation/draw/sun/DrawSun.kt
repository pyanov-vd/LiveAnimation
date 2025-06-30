package com.pyanov.liveanimation.draw.sun

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.draw.toRadians
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val SUN_AND_RAY_COLOR = Color(0xFFF5D442)
private const val RAYS_COUNT = 24
private const val RAYS_LENGTH_DP = 15
private const val RAY_STROKE_WIDTH_DP = 3
private const val SUN_RAY_ROTATION_DURATION_MS = 30 * 1000

@Composable
fun DrawSun(
    modifier: Modifier = Modifier,
) {
    val rotationAngle = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        rotationAngle.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = SUN_RAY_ROTATION_DURATION_MS,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    BoxWithConstraints(modifier = modifier) {
        val sunDiameterPx = constraints.maxWidth.toFloat()
        val sunActualRadiusPx = sunDiameterPx / 2f
        val sunCenter = Offset(sunActualRadiusPx, sunActualRadiusPx)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val rayStrokeWidthPx = RAY_STROKE_WIDTH_DP.dp.toPx()
            val overallRotationRad = toRadians(rotationAngle.value)

            for (i in 0 until RAYS_COUNT) {
                val rayLengthPx = if (i % 2 == 0) {
                    RAYS_LENGTH_DP.dp.toPx()
                } else {
                    (RAYS_LENGTH_DP - 5).dp.toPx()
                }
                val baseAngleRad = (2 * PI / RAYS_COUNT * i).toFloat()
                val currentAngleRad = baseAngleRad + overallRotationRad

                val startX = sunCenter.x + sunActualRadiusPx * cos(currentAngleRad)
                val startY = sunCenter.y + sunActualRadiusPx * sin(currentAngleRad)
                val endX = sunCenter.x + (sunActualRadiusPx + rayLengthPx) * cos(currentAngleRad)
                val endY = sunCenter.y + (sunActualRadiusPx + rayLengthPx) * sin(currentAngleRad)

                drawLine(
                    color = SUN_AND_RAY_COLOR,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = rayStrokeWidthPx,
                    cap = StrokeCap.Round
                )
            }

            drawCircle(
                color = SUN_AND_RAY_COLOR,
                radius = sunActualRadiusPx,
                center = sunCenter
            )
        }
    }
}