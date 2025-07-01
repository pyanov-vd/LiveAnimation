package com.pyanov.liveanimation.draw.mountains

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.draw.eyes.calculateEyesParamsByCanvasSize
import com.pyanov.liveanimation.draw.eyes.drawEyes

private const val DELAY = 200
private const val LABEL = "middleLayerOffsetY"

@Composable
fun MountainMiddle(
    animateMountainLayers: Boolean,
    isPasswordActuallyVisible: Boolean,
    pupilOffsetY: Float,
) {
    val middleLayerOffsetY by createOffsetAnimation(
        visibleFactor = animateMountainLayers,
        delayMillis = DELAY,
        label = LABEL
    )
    val middleLayerAlpha by createAlphaAnimation(
        visibleFactor = animateMountainLayers,
        delayMillis = DELAY,
        label = LABEL
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = middleLayerOffsetY.dp)
            .alpha(middleLayerAlpha)
    ) {
        val width = size.width
        val height = size.height
        drawMountainLayer(
            canvasWidth = width,
            canvasHeight = height,
            color = Color(0xFF575da5),
            layerHeight = height * 0.35f,
            startY = height * 0.2f,
            mountains = MiddleMountains
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = middleLayerOffsetY.dp)
            .alpha(middleLayerAlpha)
    ) {
        val eyesParams = calculateEyesParamsByCanvasSize(
            size = size,
            positionOffsetRatio = Offset(0.7f, 0.8f),
            pupilOffsetY = pupilOffsetY
        )
        drawEyes(eyesParams)
    }
}