package com.pyanov.liveanimation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale

@Composable
fun MountainAnim(
    isAnimatedAlready: Boolean = false
) {
    val density = LocalDensity.current
    val isKeyboardVisible = WindowInsets.ime.getBottom(density) > 0

    var animateAuthFields by remember { mutableStateOf(isAnimatedAlready) }

    LaunchedEffect(Unit) {
        animateAuthFields = true
    }

    // Анимация смещения зрачков
    val pupilOffsetY by animateFloatAsState(
        targetValue = if (isKeyboardVisible) 10f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "pupilOffset"
    )

    // Анимация для AuthInputFields (масштаб и прозрачность)
    val authInputFieldsScale by animateFloatAsState(
        targetValue = if (animateAuthFields) 1f else 0f,
        animationSpec = tween(durationMillis = 850, delayMillis = 550),
        label = "authInputFieldsScale"
    )
    val authInputFieldsAlpha by animateFloatAsState(
        targetValue = if (animateAuthFields) 1f else 0f,
        animationSpec = tween(durationMillis = 850, delayMillis = 550),
        label = "authInputFieldsAlpha"
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
        // Фон с горами и глазами
        MountainBackground(
            animateMountainLayers = animateAuthFields,
            pupilOffsetY = pupilOffsetY
        )

        // Поля ввода
        AuthInputFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .offset(y = 56.dp)
                .align(Alignment.Center)
                .scale(authInputFieldsScale)
                .alpha(authInputFieldsAlpha)
        )
    }
} 