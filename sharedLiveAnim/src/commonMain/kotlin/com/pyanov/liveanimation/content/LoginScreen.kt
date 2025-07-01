package com.pyanov.liveanimation.content

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    isAnimatedAlready: Boolean = false,
    passwordPreviewVisibility: Boolean = true,
) {
    val density = LocalDensity.current
    val isKeyboardVisible = WindowInsets.ime.getBottom(density) > 0

    var animateAuthFields by remember { mutableStateOf(isAnimatedAlready) }
    var isPasswordVisible by remember { mutableStateOf(passwordPreviewVisibility) }

    LaunchedEffect(Unit) {
        delay(50) //хак для того, чтобы отображалась анимация на ios
        animateAuthFields = true
    }

    // Анимация смещения зрачков
    val pupilOffsetY by animateFloatAsState(
        targetValue = if (isKeyboardVisible) 20f else 0f,
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
        MountainsContent(
            animateMountainLayers = animateAuthFields,
            pupilOffsetY = pupilOffsetY,
            isPasswordActuallyVisible = isPasswordVisible // Pass the state here
        )

        // Поля ввода
        AuthInputFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .offset(y = 26.dp)
                .align(Alignment.Center)
                .scale(authInputFieldsScale)
                .alpha(authInputFieldsAlpha),
            isPasswordVisible = isPasswordVisible, // Pass the state here
            onPasswordVisibilityChange = { isPasswordVisible = it } // Pass the callback here
        )
    }
}
