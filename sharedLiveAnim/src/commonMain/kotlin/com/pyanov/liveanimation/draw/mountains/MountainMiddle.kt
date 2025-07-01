package com.pyanov.liveanimation.draw.mountains

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
    isKeyboardVisible: Boolean,
    isPasswordActuallyVisible: Boolean,
    pupilOffset: Offset,
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

    val realPupilYoffsetByConditions = when {
        !isKeyboardVisible -> 0f
        isPasswordActuallyVisible -> 20f
        else -> -20f
    }

    val animatedExtraYOffset by animateFloatAsState(
        targetValue = realPupilYoffsetByConditions,
        animationSpec = tween(durationMillis = 300),
        label = "middleEyesExtraYOffset"
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
            pupilOffset = pupilOffset.copy(y = animatedExtraYOffset)
        )
        drawEyes(eyesParams)
    }
}