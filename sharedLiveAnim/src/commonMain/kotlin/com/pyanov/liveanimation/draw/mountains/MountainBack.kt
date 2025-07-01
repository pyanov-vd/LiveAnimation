package com.pyanov.liveanimation.draw.mountains

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.CLOUD_HEIGHT
import com.pyanov.liveanimation.MOUNTAINS_ANIM_DURATION
import com.pyanov.liveanimation.MOUNTAIN_VISUAL_HEIGHT_RATION
import com.pyanov.liveanimation.MOUNTAIN_VISUAL_TOP_RATIO
import com.pyanov.liveanimation.draw.eyes.calculateEyesParamsByCanvasSize
import com.pyanov.liveanimation.draw.eyes.drawEyes
import liveanimation.sharedliveanim.generated.resources.Res
import liveanimation.sharedliveanim.generated.resources.ill_cloud
import org.jetbrains.compose.resources.painterResource

private const val CLOUD_ANIM_DURATION = 400

const val eyesVerticalPositionOnMountainRatio = 0.6f

@Composable
fun MountainBack(
    animateMountainLayers: Boolean,
    isKeyboardVisible: Boolean,
    isPasswordActuallyVisible: Boolean,
    pupilOffset: Offset,
) {

    val backLayerOffsetY by createOffsetAnimation(
        visibleFactor = animateMountainLayers,
        label = "backLayerOffsetY"
    )
    val backLayerAlpha by createAlphaAnimation(
        visibleFactor = animateMountainLayers,
        label = "backLayerAlpha"
    )

    BoxWithConstraints {

        val cloudInitialXOffsetDp = -CLOUD_HEIGHT.dp - 20.dp
        val cloudPositionWhenPasswordVisibleDp = -(CLOUD_HEIGHT.dp * 2 / 3)
        val eyeCenterXInDp = maxWidth * 0.32f
        val cloudPositionWhenPasswordHiddenDp = eyeCenterXInDp - (CLOUD_HEIGHT.dp / 2)

        val mountainVisualTopDp = maxHeight * MOUNTAIN_VISUAL_TOP_RATIO
        val mountainVisualHeightDp = maxHeight * MOUNTAIN_VISUAL_HEIGHT_RATION

        val targetEyeAndCloudCenterYDp =
            mountainVisualTopDp + (mountainVisualHeightDp * eyesVerticalPositionOnMountainRatio)

        val targetCloudXOffsetDp = when {
            !animateMountainLayers -> cloudInitialXOffsetDp
            isKeyboardVisible && !isPasswordActuallyVisible -> cloudPositionWhenPasswordHiddenDp
            else -> cloudPositionWhenPasswordVisibleDp
        }

        val animatedCloudXOffsetDp by animateDpAsState(
            targetValue = targetCloudXOffsetDp,
            animationSpec = tween(durationMillis = if (animateMountainLayers) CLOUD_ANIM_DURATION else MOUNTAINS_ANIM_DURATION),
            label = "cloudOffsetX"
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = backLayerOffsetY.dp)
                .alpha(backLayerAlpha)
        ) {
            val width = size.width
            val height = size.height
            drawMountainLayer(
                canvasWidth = width,
                canvasHeight = height,
                color = Color(0xFFa17598),
                layerHeight = height * MOUNTAIN_VISUAL_HEIGHT_RATION,
                startY = height * MOUNTAIN_VISUAL_TOP_RATIO,
                mountains = BackMountains
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = backLayerOffsetY.dp)
                .alpha(backLayerAlpha)
        ) {
            val eyesParams = calculateEyesParamsByCanvasSize(
                size = size,
                positionOffsetRatio = Offset(0.32f, 0.6f),
                pupilOffset = pupilOffset
            )
            drawEyes(eyesParams)
        }

        Image(
            painter = painterResource(Res.drawable.ill_cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .size(CLOUD_HEIGHT.dp)
                .offset(
                    x = animatedCloudXOffsetDp,
                    y = targetEyeAndCloudCenterYDp - (CLOUD_HEIGHT.dp / 2)
                ),
            alpha = 0.97f,
            contentScale = ContentScale.FillWidth
        )
    }

}