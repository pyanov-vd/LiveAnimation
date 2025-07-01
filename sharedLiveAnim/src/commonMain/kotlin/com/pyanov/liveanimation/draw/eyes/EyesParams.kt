package com.pyanov.liveanimation.draw.eyes

import androidx.compose.ui.geometry.Offset

private const val eyeSpacingMultiplayer = 0.45f
data class EyesParams(
    val center: Offset,
    val eyeSize: Float,
    val eyeSpacing: Float = eyeSize * eyeSpacingMultiplayer,
    val pupilOffsetY: Float
)
