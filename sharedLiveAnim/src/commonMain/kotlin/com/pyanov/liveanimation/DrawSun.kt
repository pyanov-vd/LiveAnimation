package com.pyanov.liveanimation

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SunWithEyes(
    modifier: Modifier = Modifier,
    pupilOffsetY: Float
) {
    Canvas(modifier = modifier) {
        val sunActualRadiusPx = size.minDimension / 2f

        drawCircle(
            color = Color(0xFFFFC107),
            radius = sunActualRadiusPx,
            center = center
        )

        val eyeCenterXOnSun = center.x
        val eyeCenterYOnSun = center.y
        val sunEyeSizePx = sunActualRadiusPx * 0.35f

        drawEyes(
            eyeCenterX = eyeCenterXOnSun,
            eyeCenterY = eyeCenterYOnSun,
            eyeSize = sunEyeSizePx,
            pupilOffsetY = pupilOffsetY
        )
    }
}
