package com.pyanov.liveanimation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
    // Анимация для заднего слоя (выезжает снизу)
    val backLayerOffsetY by animateFloatAsState(
        targetValue = if (animateMountainLayers) 0f else 2000f, // 2000f - значение для выезда за экран снизу
        animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 0),
        label = "backLayerOffsetY"
    )
    val backLayerAlpha by animateFloatAsState(
        targetValue = if (animateMountainLayers) 1f else 0f,
        animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 0),
        label = "backLayerAlpha"
    )

    // Анимация для среднего слоя (выезжает снизу)
    val middleLayerOffsetY by animateFloatAsState(
        targetValue = if (animateMountainLayers) 0f else 2000f, // 2000f - значение для выезда за экран снизу
        animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 200),
        label = "middleLayerOffsetY"
    )
    val middleLayerAlpha by animateFloatAsState(
        targetValue = if (animateMountainLayers) 1f else 0f,
        animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 200),
        label = "middleLayerAlpha"
    )

    // Анимация для переднего слоя (выезжает снизу)
    val frontLayerOffsetY by animateFloatAsState(
        targetValue = if (animateMountainLayers) 0f else 2000f, // 2000f - значение для выезда за экран снизу
        animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 350),
        label = "frontLayerOffsetY"
    )
    val frontLayerAlpha by animateFloatAsState(
        targetValue = if (animateMountainLayers) 1f else 0f,
        animationSpec = tween(durationMillis = ANIM_DURATION, delayMillis = 350),
        label = "frontLayerAlpha"
    )

    // Слои гор на горизонте
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
            layerHeight = height * 0.4f,
            startY = height * 0.1f
        )
    }

    // Глаза на левой горе заднего слоя
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = backLayerOffsetY.dp) // Применяем то же смещение по Y, что и к заднему слою
            .alpha(backLayerAlpha) // И ту же прозрачность
    ) {
        val width = size.width
        val height = size.height

        // Координаты для левой горы заднего слоя
        val backLayerStartY = height * 0.1f
        val backLayerHeight = height * 0.4f

        // Примерные координаты для центра глаз на левой горе
        val eyeCenterX = width * 0.32f // Смещено к центру левой горы
        val eyeCenterY = backLayerStartY + backLayerHeight * 0.6f // Чуть выше середины горы
        val eyeSize = backLayerHeight * 0.1f // Уменьшаем размер глаз

        drawEyes(
            eyeCenterX = eyeCenterX,
            eyeCenterY = eyeCenterY,
            eyeSize = eyeSize,
            pupilOffsetY = pupilOffsetY
        )
    }

    // Облако из PNG
    Image(
        painter = painterResource(Res.drawable.ill_cloud),
        contentDescription = "Cloud",
        modifier = Modifier
            .size(200.dp)
            .offset(y = backLayerOffsetY.dp)
            .alpha(backLayerAlpha)
            .border(width = 2.dp, color = Color.Red),
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