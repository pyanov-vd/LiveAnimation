package com.pyanov.liveanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import liveanimation.sharedliveanim.generated.resources.Res
import liveanimation.sharedliveanim.generated.resources.ill_sunglasses
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val SUN_AND_RAY_COLOR = Color(0xFFF5D442)
private val MOUTH_COLOR = Color(0xFF3b467d)
private const val RAYS_COUNT = 24
private const val RAYS_LENGTH_DP = 15
private const val RAY_STROKE_WIDTH_DP = 3
private const val MOUTH_WIDTH_RATIO = 0.4f
private const val SMILE_DEPTH_RATIO = 0.15f
private const val MOUTH_OFFSET_Y_DP = 20
private const val SUN_EYE_SIZE_RATIO = 0.35f
private const val SUN_RAY_ROTATION_DURATION_MS = 30 * 1000

private val SUNGLASSES_SIZE = 60.dp
private val EYES_AND_SUNGLASSES_OFFSET_Y = (-3).dp
private val EYES_AND_SUNGLASSES_INVISIBLE_OFFSET_Y = (-10).dp

@Composable
fun DrawSun(
    modifier: Modifier = Modifier,
    pupilOffsetY: Float,
    isPasswordActuallyVisible: Boolean,
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

    // Анимация смещения очков
    val sunglassesOffsetY by animateDpAsState(
        targetValue = if (isPasswordActuallyVisible) EYES_AND_SUNGLASSES_INVISIBLE_OFFSET_Y else EYES_AND_SUNGLASSES_OFFSET_Y,
        animationSpec = tween(durationMillis = 300),
        label = "sunglassesOffset"
    )

    val sunglassesAlpha by animateFloatAsState(
        targetValue = if (isPasswordActuallyVisible) 0f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "sunglassesAlpha"
    )

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

            val sunEyeSizePx = sunActualRadiusPx * SUN_EYE_SIZE_RATIO
            drawEyes(
                eyeCenterX = sunCenter.x,
                eyeCenterY = sunCenter.y + EYES_AND_SUNGLASSES_OFFSET_Y.value,
                eyeSize = sunEyeSizePx,
                pupilOffsetY = pupilOffsetY
            )

            val mouthOffsetYPx = MOUTH_OFFSET_Y_DP.dp.toPx()
            val mouthWidth = sunDiameterPx * MOUTH_WIDTH_RATIO
            val mouthHalfWidth = mouthWidth / 2

            val mouthPath = Path().apply {
                moveTo(sunCenter.x - mouthHalfWidth, sunCenter.y + mouthOffsetYPx)
                quadraticTo(
                    sunCenter.x, sunCenter.y + mouthOffsetYPx + (sunDiameterPx * SMILE_DEPTH_RATIO),
                    sunCenter.x + mouthHalfWidth, sunCenter.y + mouthOffsetYPx
                )
            }
            drawPath(
                path = mouthPath,
                color = MOUTH_COLOR,
                style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Image(
            painter = painterResource(Res.drawable.ill_sunglasses),
            contentDescription = "sunglasses",
            modifier = Modifier
                .size(SUNGLASSES_SIZE)
                .align(Alignment.Center)
                .offset(y = sunglassesOffsetY)
                .alpha(sunglassesAlpha)
        )
    }
}

private fun toRadians(degrees: Float): Float = degrees * PI.toFloat() / 180f