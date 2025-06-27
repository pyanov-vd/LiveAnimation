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

private const val ANIM_DURATION = 850

@Composable
fun MountainBackground(
    animateMountainLayers: Boolean,
    pupilOffsetY: Float,
) {
    BoxWithConstraints {
        val maxHeightDp = this.maxHeight

        val cloudHeightDp = 200.dp // Высота облака

        // Коэффициенты для геометрии задней горы и положения глаз
        val mountainVisualTopRatio = 0.1f        // Верх горы от верха компонента
        val mountainVisualHeightRatio = 0.4f     // Высота горы относительно высоты компонента
        val eyesVerticalPositionOnMountainRatio = 0.6f // Положение глаз на горе (0.0 - вершина, 1.0 - основание)

        // Рассчитываем целевую Y-координату для центра глаз и облака в Dp
        val mountainVisualTopDp = maxHeightDp * mountainVisualTopRatio
        val mountainVisualHeightDp = maxHeightDp * mountainVisualHeightRatio
        val targetEyeAndCloudCenterYDp = mountainVisualTopDp + (mountainVisualHeightDp * eyesVerticalPositionOnMountainRatio)

        // Анимация для заднего слоя (выезжает снизу)
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

        // Анимация для среднего слоя
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

        // Анимация для переднего слоя
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

        // Анимация для X-координаты облака
        val cloudInitialXOffsetDp = -cloudHeightDp - 20.dp // Полностью за левым краем
        val cloudTargetXOffsetDp = -(cloudHeightDp * 2 / 3) // Целевое положение: 2/3 за краем

        val animatedCloudXOffsetDp by animateDpAsState(
            targetValue = if (animateMountainLayers) cloudTargetXOffsetDp else cloudInitialXOffsetDp,
            animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 0), // Синхронно с появлением горы
            label = "cloudOffsetX"
        )

        // Слой задней горы (только форма)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = backLayerOffsetY.dp)
                .alpha(backLayerAlpha)
        ) {
            val width = size.width
            val height = size.height // Соответствует maxHeightDp в пикселях
            drawBackMountainLayer(
                canvasWidth = width,
                canvasHeight = height, // Используем высоту Canvas
                color = Color(0xFFa17598),
                layerHeight = height * mountainVisualHeightRatio, // Используем тот же коэффициент
                startY = height * mountainVisualTopRatio          // Используем тот же коэффициент
            )
        }

        // Глаза на левой горе заднего слоя
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = backLayerOffsetY.dp)
                .alpha(backLayerAlpha)
        ) {
            val canvasWidthPx = size.width
            val canvasHeightPx = size.height // Соответствует maxHeightDp в пикселях

            // Рассчитываем Y-координату центра глаз в пикселях, используя те же коэффициенты
            val mountainVisualTopPx = canvasHeightPx * mountainVisualTopRatio
            val mountainVisualHeightPx = canvasHeightPx * mountainVisualHeightRatio
            val eyeCenterY_forDrawEyes = mountainVisualTopPx + (mountainVisualHeightPx * eyesVerticalPositionOnMountainRatio)

            val eyeCenterX = canvasWidthPx * 0.32f // Примерное положение по X
            val eyeSize = mountainVisualHeightPx * 0.1f // Размер глаз относительно высоты горы

            drawEyes(
                eyeCenterX = eyeCenterX,
                eyeCenterY = eyeCenterY_forDrawEyes,
                eyeSize = eyeSize,
                pupilOffsetY = pupilOffsetY
            )
        }

        // Облако из PNG
        Image(
            painter = painterResource(Res.drawable.ill_cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .size(cloudHeightDp)
                .offset(
                    x = animatedCloudXOffsetDp, // Используем анимированное X-смещение
                    // Y-смещение теперь не зависит от backLayerOffsetY и центрировано по targetEyeAndCloudCenterYDp
                    y = targetEyeAndCloudCenterYDp - (cloudHeightDp / 2)
                ),
                alpha = 0.8f,
            contentScale = ContentScale.FillWidth
        )

        // Средний слой гор
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
                layerHeight = height * 0.35f, // Эти коэффициенты могут быть другими
                startY = height * 0.2f
            )
        }

        // Передний слой гор
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
                layerHeight = height * 0.3f, // Эти коэффициенты могут быть другими
                startY = height * 0.3f
            )
        }
    }
}
