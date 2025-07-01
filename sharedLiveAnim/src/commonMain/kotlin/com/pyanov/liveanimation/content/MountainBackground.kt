package com.pyanov.liveanimation.content

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.MOUNTAINS_ANIM_DURATION
import com.pyanov.liveanimation.draw.eyes.EyesParams
import com.pyanov.liveanimation.draw.eyes.drawEyes
import com.pyanov.liveanimation.draw.mountains.BackMountains
import com.pyanov.liveanimation.draw.mountains.MiddleMountains
import com.pyanov.liveanimation.draw.mountains.MountainBack
import com.pyanov.liveanimation.draw.mountains.createAlphaAnimation
import com.pyanov.liveanimation.draw.mountains.createOffsetAnimation
import com.pyanov.liveanimation.draw.mountains.drawMountainLayer
import com.pyanov.liveanimation.draw.sun.DrawSun
import liveanimation.sharedliveanim.generated.resources.Res
import liveanimation.sharedliveanim.generated.resources.ill_cloud
import liveanimation.sharedliveanim.generated.resources.ill_sunglasses
import org.jetbrains.compose.resources.painterResource


private const val CLOUD_ANIM_DURATION = 400

private val SUNGLASSES_SIZE = 100.dp
private val EYES_AND_SUNGLASSES_OFFSET_Y = (-67).dp
private val EYES_AND_SUNGLASSES_INVISIBLE_OFFSET_Y = (-100).dp

@Composable
fun MountainBackground(
    pupilOffsetY: Float,
    animateMountainLayers: Boolean,
    isPasswordActuallyVisible: Boolean,
) {
    BoxWithConstraints {
        val maxHeightDp = this.maxHeight
        val maxWidthDp = this.maxWidth

        val cloudHeightDp = 200.dp
        val sunDiameterDp = 100.dp

        val mountainVisualTopRatio = 0.1f
        val mountainVisualHeightRatio = 0.4f
        val eyesVerticalPositionOnMountainRatio = 0.6f

        val mountainVisualTopDp = maxHeightDp * mountainVisualTopRatio
        val mountainVisualHeightDp = maxHeightDp * mountainVisualHeightRatio
        val targetEyeAndCloudCenterYDp =
            mountainVisualTopDp + (mountainVisualHeightDp * eyesVerticalPositionOnMountainRatio)

        val backLayerOffsetY by createOffsetAnimation(
            animateMountainLayers = animateMountainLayers,
            delayMillis = 0,
            label = "backLayerOffsetY"
        )
        val backLayerAlpha by createAlphaAnimation(
            animateMountainLayers = animateMountainLayers,
            delayMillis = 0,
            label = "backLayerAlpha"
        )

        val middleLayerOffsetY by createOffsetAnimation(
            animateMountainLayers = animateMountainLayers,
            delayMillis = 200,
            label = "middleLayerOffsetY"
        )
        val middleLayerAlpha by createAlphaAnimation(
            animateMountainLayers = animateMountainLayers,
            delayMillis = 200,
            label = "middleLayerAlpha"
        )

        val cloudInitialXOffsetDp = -cloudHeightDp - 20.dp
        val cloudPositionWhenPasswordVisibleDp = -(cloudHeightDp * 2 / 3)
        val eyeCenterXInDp = maxWidthDp * 0.32f
        val cloudPositionWhenPasswordHiddenDp = eyeCenterXInDp - (cloudHeightDp / 2)

        val targetCloudXOffsetDp = if (!animateMountainLayers) {
            cloudInitialXOffsetDp
        } else {
            if (isPasswordActuallyVisible) {
                cloudPositionWhenPasswordVisibleDp
            } else {
                cloudPositionWhenPasswordHiddenDp
            }
        }

        val animatedCloudXOffsetDp by animateDpAsState(
            targetValue = targetCloudXOffsetDp,
            animationSpec = tween(durationMillis = if (animateMountainLayers) CLOUD_ANIM_DURATION else MOUNTAINS_ANIM_DURATION),
            label = "cloudOffsetX"
        )

        val sunInitialXOffsetDp = maxWidthDp
        val sunTargetXOffsetDp = (maxWidthDp * 2 / 3) - (sunDiameterDp / 2)

        val animatedSunXOffsetDp by animateDpAsState(
            targetValue = if (animateMountainLayers) sunTargetXOffsetDp else sunInitialXOffsetDp,
            animationSpec = tween(
                durationMillis = MOUNTAINS_ANIM_DURATION,
                easing = FastOutSlowInEasing
            ),
            label = "sunXOffset"
        )

        DrawSun(
            modifier = Modifier
                .size(sunDiameterDp)
                .align(Alignment.TopStart)
                .offset(
                    x = animatedSunXOffsetDp,
                    y = 50.dp
                )
        )

        val configuration = LocalWindowInfo.current
        val screenWidthDp = (configuration.containerSize.width * (2.2f / 4f)).toInt()

        Image(
            painter = painterResource(Res.drawable.ill_cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .size(cloudHeightDp)
                .offset {
                    IntOffset(screenWidthDp, 150)
                },
            alpha = 0.97f,
            contentScale = ContentScale.FillWidth
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
                layerHeight = height * mountainVisualHeightRatio,
                startY = height * mountainVisualTopRatio,
                mountains = BackMountains
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = backLayerOffsetY.dp)
                .alpha(backLayerAlpha)
        ) {
            val canvasWidthPx = size.width
            val canvasHeightPx = size.height
            val mountainVisualTopPx = canvasHeightPx * mountainVisualTopRatio
            val mountainVisualHeightPx = canvasHeightPx * mountainVisualHeightRatio
            val eyeCenter = androidx.compose.ui.geometry.Offset(
                x = canvasWidthPx * 0.32f,
                y = mountainVisualTopPx + (mountainVisualHeightPx * eyesVerticalPositionOnMountainRatio)
            )
            val eyeSize = mountainVisualHeightPx * 0.1f
            drawEyes(
                EyesParams(
                    center = eyeCenter,
                    eyeSize = eyeSize,
                    pupilOffsetY = pupilOffsetY
                )
            )
        }

        Image(
            painter = painterResource(Res.drawable.ill_cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .size(cloudHeightDp)
                .offset(
                    x = animatedCloudXOffsetDp,
                    y = targetEyeAndCloudCenterYDp - (cloudHeightDp / 2)
                ),
            alpha = 0.97f,
            contentScale = ContentScale.FillWidth
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
            val canvasWidthPx = size.width
            val canvasHeightPx = size.height
            val mountainVisualTopPx = canvasHeightPx * mountainVisualTopRatio
            val mountainVisualHeightPx = canvasHeightPx * mountainVisualHeightRatio
            val eyeCenter = androidx.compose.ui.geometry.Offset(
                x = canvasWidthPx * 0.7f,
                y = mountainVisualTopPx + (mountainVisualHeightPx * 0.8f)
            )
            val eyeSize = mountainVisualHeightPx * 0.1f
            drawEyes(
                EyesParams(
                    center = eyeCenter,
                    eyeSize = eyeSize,
                    pupilOffsetY = pupilOffsetY
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
        Image(
            painter = painterResource(Res.drawable.ill_sunglasses),
            contentDescription = "sunglasses",
            modifier = Modifier
                .size(SUNGLASSES_SIZE)
                .align(Alignment.Center)
                .offset(x = 78.dp, y = sunglassesOffsetY)
                .alpha(sunglassesAlpha)
        )


        MountainBack(
            animateMountainLayers = animateMountainLayers
        )
    }
}