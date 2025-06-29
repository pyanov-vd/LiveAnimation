package com.pyanov.liveanimation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.designSystem.LATheme
import liveanimation.sharedliveanim.generated.resources.Res
import liveanimation.sharedliveanim.generated.resources.ill_cloud
import liveanimation.sharedliveanim.generated.resources.ill_sunglasses
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val ANIM_DURATION = 850
private const val CLOUD_ANIM_DURATION = 400

private val SUNGLASSES_SIZE = 100.dp
private val EYES_AND_SUNGLASSES_OFFSET_Y = (-67).dp
private val EYES_AND_SUNGLASSES_INVISIBLE_OFFSET_Y = (-100).dp

@Composable
fun MountainBackground(
    animateMountainLayers: Boolean,
    pupilOffsetY: Float,
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

        val backLayerOffsetY by animateFloatAsState(
            targetValue = if (animateMountainLayers) 0f else 2000f,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 0),
            label = "backLayerOffsetY"
        )
        val backLayerAlpha by animateFloatAsState(
            targetValue = if (animateMountainLayers) 1f else 0f,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 0),
            label = "backLayerAlpha"
        )

        val middleLayerOffsetY by animateFloatAsState(
            targetValue = if (animateMountainLayers) 0f else 2000f,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 200),
            label = "middleLayerOffsetY"
        )
        val middleLayerAlpha by animateFloatAsState(
            targetValue = if (animateMountainLayers) 1f else 0f,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 200),
            label = "middleLayerAlpha"
        )

        val frontLayerOffsetY by animateFloatAsState(
            targetValue = if (animateMountainLayers) 0f else 2000f,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 350),
            label = "frontLayerOffsetY"
        )
        val frontLayerAlpha by animateFloatAsState(
            targetValue = if (animateMountainLayers) 1f else 0f,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 350),
            label = "frontLayerAlpha"
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
            animationSpec = tween(durationMillis = if (animateMountainLayers) CLOUD_ANIM_DURATION else ANIM_DURATION),
            label = "cloudOffsetX"
        )

        val sunInitialXOffsetDp = maxWidthDp
        val sunTargetXOffsetDp = (maxWidthDp * 2 / 3) - (sunDiameterDp / 2)

        val animatedSunXOffsetDp by animateDpAsState(
            targetValue = if (animateMountainLayers) sunTargetXOffsetDp else sunInitialXOffsetDp,
            animationSpec = tween(durationMillis = ANIM_DURATION, easing = FastOutSlowInEasing),
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
            drawBackMountainLayer(
                canvasWidth = width,
                canvasHeight = height,
                color = Color(0xFFa17598),
                layerHeight = height * mountainVisualHeightRatio,
                startY = height * mountainVisualTopRatio
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
            val eyeCenterYForDrawEyes =
                mountainVisualTopPx + (mountainVisualHeightPx * eyesVerticalPositionOnMountainRatio)
            val eyeCenterXForDrawEyes = canvasWidthPx * 0.32f
            val eyeSize = mountainVisualHeightPx * 0.1f

            drawEyes(
                eyeCenterX = eyeCenterXForDrawEyes,
                eyeCenterY = eyeCenterYForDrawEyes,
                eyeSize = eyeSize,
                pupilOffsetY = pupilOffsetY
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
            drawMiddleMountainLayer(
                canvasWidth = width,
                canvasHeight = height,
                color = Color(0xFF575da5),
                layerHeight = height * 0.35f,
                startY = height * 0.2f
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
            val eyeCenterYForDrawEyes =
                mountainVisualTopPx + (mountainVisualHeightPx * 0.8f)
            val eyeCenterXForDrawEyes = canvasWidthPx * 0.7f
            val eyeSize = mountainVisualHeightPx * 0.1f

            drawEyes(
                eyeCenterX = eyeCenterXForDrawEyes,
                eyeCenterY = eyeCenterYForDrawEyes,
                eyeSize = eyeSize,
                pupilOffsetY = pupilOffsetY
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


        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = frontLayerOffsetY.dp)
                .alpha(frontLayerAlpha)
        ) {
            val width = size.width
            val height = size.height
            drawFrontMountainLayer(
                canvasWidth = width,
                canvasHeight = height,
                color = Color(0xFF3b467d),
                layerHeight = height * 0.3f,
                startY = height * 0.3f
            )
        }
    }
}