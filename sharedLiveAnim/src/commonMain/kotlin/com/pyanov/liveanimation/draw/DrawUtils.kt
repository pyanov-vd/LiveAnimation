package com.pyanov.liveanimation.draw

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.PI

fun toRadians(degrees: Float): Float = degrees * PI.toFloat() / 180f

@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current
    return with(density) { this@toPx.toPx() }
}