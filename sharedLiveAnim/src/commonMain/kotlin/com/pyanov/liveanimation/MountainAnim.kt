package com.pyanov.liveanimation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.designSystem.SpacerSmall
import com.pyanov.liveanimation.designSystem.controlls.AppEditText
import com.pyanov.liveanimation.drawBackMountainLayer
import com.pyanov.liveanimation.drawFrontMountainLayer
import com.pyanov.liveanimation.drawMiddleMountainLayer
import androidx.compose.ui.draw.alpha

private const val ANIM_DURATION = 850

@Composable
fun MountainAnim() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val density = LocalDensity.current
    val isKeyboardVisible = WindowInsets.ime.getBottom(density) > 0

    var animateMountainLayers by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateMountainLayers = true
    }

    // Анимация смещения зрачков
    val pupilOffsetY by animateFloatAsState(
        targetValue = if (isKeyboardVisible) 10f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "pupilOffset"
    )

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF7383C2), // Верхний цвет
                        Color(0xFFD6B0C1), // Нижний цвет
                        Color(0xFFD6B0C1)  // Повторяем нижний цвет для более плавного перехода
                    )
                )
            )
    ) {
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

        // Основная гора
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-200).dp)
        ) {
            val width = size.width
            val height = size.height

            // Вычисляем размеры горы (четверть экрана)
            val mountainWidth = width * 0.25f
            val mountainHeight = height * 0.25f

            // Вычисляем начальную точку для центрирования
            val startX = (width - mountainWidth) / 2
            val startY = (height - mountainHeight) / 2

            drawMountain(width, height, mountainWidth, mountainHeight, startX, startY)
            drawEyes(mountainWidth, mountainHeight, startX, startY, pupilOffsetY)
        }

        // Поля ввода по центру
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .offset(y = (-50).dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Поле ввода логина
            AppEditText(
                modifier = Modifier.fillMaxWidth(),
                startLabel = "Username",
                startText = username,
                imeAction = ImeAction.Next,
                onValueChange = { username = it }
            )

            SpacerSmall()

            // Поле ввода пароля
            AppEditText(
                modifier = Modifier.fillMaxWidth(),
                startLabel = "Password",
                startText = password,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { password = it },
                isPasswordVisible = isPasswordVisible,
                onPasswordVisibilityChange = { isPasswordVisible = it }
            )
        }
    }
} 