package com.pyanov.liveanimation.draw.mountains

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private const val DELAY = 350
private const val LABEL = "frontLayerOffsetY"

@Composable
fun MountainFront(
    animateMountainLayers: Boolean,
) {
    val frontLayerOffsetY by createOffsetAnimation(
        visibleFactor = animateMountainLayers,
        delayMillis = DELAY,
        label = LABEL
    )
    val frontLayerAlpha by createAlphaAnimation(
        visibleFactor = animateMountainLayers,
        delayMillis = DELAY,
        label = LABEL
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = frontLayerOffsetY.dp)
            .alpha(frontLayerAlpha)
    ) {
        val width = size.width
        val height = size.height
        drawMountainLayer(
            canvasWidth = width,
            canvasHeight = height,
            color = Color(0xFF3b467d),
            layerHeight = height * 0.3f,
            startY = height * 0.3f,
            mountains = FrontMountains
        )
    }
}