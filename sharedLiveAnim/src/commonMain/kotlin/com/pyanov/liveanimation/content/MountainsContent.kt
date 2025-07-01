package com.pyanov.liveanimation.content

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import com.pyanov.liveanimation.draw.mountains.MountainBack
import com.pyanov.liveanimation.draw.mountains.MountainFront
import com.pyanov.liveanimation.draw.mountains.MountainMiddle
import com.pyanov.liveanimation.draw.sun.SunLayer

@Composable
fun MountainsContent(
    animateMountainLayers: Boolean,
    isPasswordActuallyVisible: Boolean,
) {

    val density = LocalDensity.current
    val isKeyboardVisible = WindowInsets.ime.getBottom(density) > 0

    val pupilOffsetY by animateFloatAsState(
        targetValue = if (isKeyboardVisible) 20f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "pupilOffset"
    )
    val pupilOffset = remember(pupilOffsetY) { Offset(0f, pupilOffsetY) }

    SunLayer(animateMountainLayers = animateMountainLayers)

    MountainBack(
        animateMountainLayers = animateMountainLayers,
        isKeyboardVisible = isKeyboardVisible,
        isPasswordActuallyVisible = isPasswordActuallyVisible,
        pupilOffset = pupilOffset
    )

    MountainMiddle(
        animateMountainLayers = animateMountainLayers,
        isKeyboardVisible = isKeyboardVisible,
        isPasswordActuallyVisible = isPasswordActuallyVisible,
        pupilOffset = pupilOffset
    )

    MountainFront(animateMountainLayers = animateMountainLayers)
}