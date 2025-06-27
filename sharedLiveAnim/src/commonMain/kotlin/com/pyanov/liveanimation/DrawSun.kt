package com.pyanov.liveanimation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

private val SUN_AND_RAY_COLOR = Color(0xFFF5D442)
private val MOUTH_COLOR = Color(0xFF3b467d) //Color.Black
private const val RAYS_COUNT = 20
private const val RAYS_LENGTH = 15

@Composable
fun SunWithEyes(
    modifier: Modifier = Modifier,
    pupilOffsetY: Float,
) {
    BoxWithConstraints(modifier = modifier) {
        val sunDiameterPx = constraints.maxWidth.toFloat()
        val sunActualRadiusPx = sunDiameterPx / 2f
        val sunCenter = Offset(sunActualRadiusPx, sunActualRadiusPx)

        Canvas(modifier = Modifier.fillMaxSize()) {

            for (i in 0 until RAYS_COUNT) {
                val rayLengthPx = if (i % 2 == 0) {
                    RAYS_LENGTH.dp.toPx()
                } else {
                    (RAYS_LENGTH - 5).dp.toPx()
                }

                val angle = (2 * kotlin.math.PI / RAYS_COUNT * i).toFloat()
                val startX = sunCenter.x + sunActualRadiusPx * cos(angle)
                val startY = sunCenter.y + sunActualRadiusPx * sin(angle)
                val endX = sunCenter.x + (sunActualRadiusPx + rayLengthPx) * cos(angle)
                val endY = sunCenter.y + (sunActualRadiusPx + rayLengthPx) * sin(angle)

                drawLine(
                    color = SUN_AND_RAY_COLOR,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            drawCircle(
                color = SUN_AND_RAY_COLOR,
                radius = sunActualRadiusPx,
                center = sunCenter
            )

            val sunEyeSizePx = sunActualRadiusPx * 0.37f
            drawEyes(
                eyeCenterX = sunCenter.x,
                eyeCenterY = sunCenter.y - 15,
                eyeSize = sunEyeSizePx,
                pupilOffsetY = pupilOffsetY
            )

            val mouthWidthRatio = 0.4f
            val smileDepthRatio = 0.15f
            val mouthOffsetY = 15.dp.toPx()
            val mouthWidth = sunDiameterPx * mouthWidthRatio
            val mouthHalfWidth = mouthWidth / 2

            val mouthPath = Path().apply {
                moveTo(sunCenter.x - mouthHalfWidth, sunCenter.y + mouthOffsetY)
                quadraticTo(
                    sunCenter.x, sunCenter.y + mouthOffsetY + (sunDiameterPx * smileDepthRatio),
                    sunCenter.x + mouthHalfWidth, sunCenter.y + mouthOffsetY
                )
            }
            drawPath(
                path = mouthPath,
                color = MOUTH_COLOR,
                style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}