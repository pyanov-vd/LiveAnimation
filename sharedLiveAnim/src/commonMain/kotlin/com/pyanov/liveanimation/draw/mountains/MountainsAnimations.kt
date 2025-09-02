package com.pyanov.liveanimation.draw.mountains

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.pyanov.liveanimation.MOUNTAINS_ANIM_DURATION

@Composable
fun createOffsetAnimation(
    visibleFactor: Boolean,
    label: String,
    durationMillis: Int = MOUNTAINS_ANIM_DURATION,
    delayMillis: Int = 0,
): State<Float> = animateFloatAsState(
    targetValue = if (visibleFactor) 0f else 2000f,
    animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis),
    label = label
)

@Composable
fun createAlphaAnimation(
    visibleFactor: Boolean,
    label: String,
    durationMillis: Int = MOUNTAINS_ANIM_DURATION,
    delayMillis: Int = 0,
): State<Float> = animateFloatAsState(
    targetValue = if (visibleFactor) 1f else 0f,
    animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis),
    label = label
)