package com.pyanov.liveanimation

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SunWithEyes(
    modifier: Modifier = Modifier,
    pupilOffsetY: Float
) {
    Canvas(modifier = modifier) {
        val sunActualRadiusPx = size.minDimension / 2f
        val sunDiameterPx = size.minDimension

        drawCircle(
            color = Color(0xFFFFC107),
            radius = sunActualRadiusPx,
            center = center
        )

        val eyeCenterXOnSun = center.x
        val eyeCenterYOnSun = center.y
        val sunEyeSizePx = sunActualRadiusPx * 0.45f

        drawEyes(
            eyeCenterX = eyeCenterXOnSun,
            eyeCenterY = eyeCenterYOnSun - 5.dp.toPx(),
            eyeSize = sunEyeSizePx,
            pupilOffsetY = pupilOffsetY
        )

        val mouthStrokeWidthPx = 1.dp.toPx()
        val mouthOffsetYFromCenterPx = 20.dp.toPx()
        val mouthWidthRatio = 0.4f
        val smileDepthRatio = 0.15f

        val mouthCenterY = center.y + mouthOffsetYFromCenterPx
        val mouthHalfWidth = (sunDiameterPx * mouthWidthRatio) / 2f

        val mouthStartX = center.x - mouthHalfWidth
        val mouthEndX = center.x + mouthHalfWidth
        val mouthControlX = center.x
        val mouthControlY = mouthCenterY + (sunDiameterPx * smileDepthRatio)

        val path = Path().apply {
            moveTo(mouthStartX, mouthCenterY)
            quadraticTo(
                mouthControlX, mouthControlY,
                mouthEndX, mouthCenterY
            )
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = mouthStrokeWidthPx, cap = StrokeCap.Round)
        )
    }
}
