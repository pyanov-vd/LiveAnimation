package com.pyanov.liveanimation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import liveanimation.sharedliveanim.generated.resources.Res
import liveanimation.sharedliveanim.generated.resources.ill_cloud
import org.jetbrains.compose.resources.painterResource

private const val ANIM_DURATION = 850 // Длительность для основных анимаций
private const val CLOUD_ANIM_DURATION = 400 // Длительность для анимации облака при смене видимости пароля

@Composable
fun MountainBackground(
    animateMountainLayers: Boolean,
    pupilOffsetY: Float,
    isPasswordActuallyVisible: Boolean, // Новый параметр
) {
    BoxWithConstraints {
        val maxHeightDp = this.maxHeight
        val maxWidthDp = this.maxWidth // Получаем максимальную ширину

        val cloudHeightDp = 200.dp // Высота облака (предполагаем, что ширина примерно такая же для расчетов)

        val mountainVisualTopRatio = 0.1f
        val mountainVisualHeightRatio = 0.4f
        val eyesVerticalPositionOnMountainRatio = 0.6f

        val mountainVisualTopDp = maxHeightDp * mountainVisualTopRatio
        val mountainVisualHeightDp = maxHeightDp * mountainVisualHeightRatio
        val targetEyeAndCloudCenterYDp = mountainVisualTopDp + (mountainVisualHeightDp * eyesVerticalPositionOnMountainRatio)

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

        // Логика для X-координаты облака
        val cloudInitialXOffsetDp = -cloudHeightDp - 20.dp // Полностью за левым краем (для стартовой анимации)

        // Позиция облака, когда пароль ВИДЕН (стартовая "парковочная" позиция)
        val cloudPositionWhenPasswordVisibleDp = -(cloudHeightDp * 2 / 3)

        // Позиция облака, когда пароль СКРЫТ (центр облака на уровне центра X глаз)
        // Глаза рисуются в Canvas с eyeCenterX = canvasWidthPx * 0.32f.
        // canvasWidthPx соответствует maxWidthDp родительского BoxWithConstraints.
        val eyeCenterXInDp = maxWidthDp * 0.32f
        val cloudPositionWhenPasswordHiddenDp = eyeCenterXInDp - (cloudHeightDp / 2) // Левый край облака для центрирования

        val targetCloudXOffsetDp = if (!animateMountainLayers) {
            cloudInitialXOffsetDp // 1. Начальное состояние: за экраном (перед стартовой анимацией)
        } else {
            // 2. После стартовой анимации, позиция зависит от видимости пароля
            if (isPasswordActuallyVisible) {
                cloudPositionWhenPasswordVisibleDp // Пароль виден
            } else {
                cloudPositionWhenPasswordHiddenDp // Пароль скрыт
            }
        }

        val animatedCloudXOffsetDp by animateDpAsState(
            targetValue = targetCloudXOffsetDp,
            // Анимация для смены видимости пароля может быть быстрее
            animationSpec = tween(durationMillis = if (animateMountainLayers) CLOUD_ANIM_DURATION else ANIM_DURATION),
            label = "cloudOffsetX"
        )

        Canvas( // Задний слой горы
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

        Canvas( // Глаза
            modifier = Modifier
                .fillMaxSize()
                .offset(y = backLayerOffsetY.dp)
                .alpha(backLayerAlpha)
        ) {
            val canvasWidthPx = size.width
            val canvasHeightPx = size.height
            val mountainVisualTopPx = canvasHeightPx * mountainVisualTopRatio
            val mountainVisualHeightPx = canvasHeightPx * mountainVisualHeightRatio
            val eyeCenterY_forDrawEyes = mountainVisualTopPx + (mountainVisualHeightPx * eyesVerticalPositionOnMountainRatio)
            val eyeCenterX_forDrawEyes = canvasWidthPx * 0.32f
            val eyeSize = mountainVisualHeightPx * 0.1f

            drawEyes(
                eyeCenterX = eyeCenterX_forDrawEyes,
                eyeCenterY = eyeCenterY_forDrawEyes,
                eyeSize = eyeSize,
                pupilOffsetY = pupilOffsetY
            )
        }

        Image( // Облако
            painter = painterResource(Res.drawable.ill_cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .size(cloudHeightDp)
                .offset(
                    x = animatedCloudXOffsetDp,
                    y = targetEyeAndCloudCenterYDp - (cloudHeightDp / 2)
                ),
            alpha = 0.97f, // Оставил альфу, если она нужна. Если нет - можно убрать.
            contentScale = ContentScale.FillWidth
        )

        Canvas( // Средний слой гор
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

        Canvas( // Передний слой гор
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
