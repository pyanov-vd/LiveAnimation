package com.pyanov.liveanimation.draw.eyes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

private const val halfMultiplayer = 0.5f
private const val mountainVisualTopRatio = 0.1f
private const val mountainVisualHeightRatio = 0.4f

fun calculateEyesParamsByCanvasSize(
    size: Size,
    positionOffsetRatio: Offset,
    pupilOffsetY: Float,
): EyesParams {
    val canvasWidthPx = size.width
    val canvasHeightPx = size.height
    val mountainVisualTopPx = canvasHeightPx * mountainVisualTopRatio
    val mountainVisualHeightPx = canvasHeightPx * mountainVisualHeightRatio
    val eyeCenter = Offset(
        x = canvasWidthPx * positionOffsetRatio.x,
        y = mountainVisualTopPx + (mountainVisualHeightPx * positionOffsetRatio.y)
    )
    val eyeSize = mountainVisualHeightPx * 0.1f

    return EyesParams(
        center = eyeCenter,
        eyeSize = eyeSize,
        pupilOffsetY = pupilOffsetY
    )
}

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