package com.pyanov.liveanimation.draw.mountains

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.pyanov.liveanimation.MOUNTAINS_ANIM_DURATION

@Composable
fun createOffsetAnimation(
    animateMountainLayers: Boolean,
    delayMillis: Int,
    label: String,
): State<Float> = animateFloatAsState(
    targetValue = if (animateMountainLayers) 0f else 2000f,
    animationSpec = tween(durationMillis = MOUNTAINS_ANIM_DURATION, delayMillis = delayMillis),
    label = label
)

@Composable
fun createAlphaAnimation(
    animateMountainLayers: Boolean,
    delayMillis: Int,
    label: String,
): State<Float> = animateFloatAsState(
    targetValue = if (animateMountainLayers) 1f else 0f,
    animationSpec = tween(durationMillis = MOUNTAINS_ANIM_DURATION, delayMillis = delayMillis),
    label = label
)