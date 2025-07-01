package com.pyanov.liveanimation.draw.eyes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

data class EyesParams(
    val center: Offset,
    val eyeSize: Float,
    val eyeSpacing: Float = eyeSize * eyeSpacingMultiplayer,
    val pupilOffsetY: Float
)

private const val halfMultiplayer = 0.5f
private const val eyeSpacingMultiplayer = 0.45f
fun DrawScope.drawEyes(params: EyesParams) {
    val eyeRadius = params.eyeSize * halfMultiplayer
    val pupilRadius = eyeRadius * halfMultiplayer
    val leftEyeCenter = Offset(params.center.x - params.eyeSpacing, params.center.y)
    val rightEyeCenter = Offset(params.center.x + params.eyeSpacing, params.center.y)


    drawCircle(
        color = Color.White,
        radius = eyeRadius,
        center = leftEyeCenter
    )
    drawCircle(
        color = Color.White,
        radius = eyeRadius,
        center = rightEyeCenter
    )
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = leftEyeCenter.copy(y = leftEyeCenter.y + params.pupilOffsetY)
    )
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = rightEyeCenter.copy(y = rightEyeCenter.y + params.pupilOffsetY)
    )
}