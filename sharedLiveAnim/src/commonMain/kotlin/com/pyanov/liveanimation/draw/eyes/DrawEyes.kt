package com.pyanov.liveanimation.draw.eyes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.pyanov.liveanimation.MOUNTAIN_VISUAL_HEIGHT_RATION
import com.pyanov.liveanimation.MOUNTAIN_VISUAL_TOP_RATIO

private const val halfMultiplayer = 0.5f

fun calculateEyesParamsByCanvasSize(
    size: Size,
    positionOffsetRatio: Offset,
    pupilOffset: Offset,
): EyesParams {
    val canvasWidthPx = size.width
    val canvasHeightPx = size.height
    val mountainVisualTopPx = canvasHeightPx * MOUNTAIN_VISUAL_TOP_RATIO
    val mountainVisualHeightPx = canvasHeightPx * MOUNTAIN_VISUAL_HEIGHT_RATION
    val eyeCenter = Offset(
        x = canvasWidthPx * positionOffsetRatio.x,
        y = mountainVisualTopPx + (mountainVisualHeightPx * positionOffsetRatio.y)
    )
    val eyeSize = mountainVisualHeightPx * 0.1f

    return EyesParams(
        center = eyeCenter,
        eyeSize = eyeSize,
        pupilOffset = pupilOffset
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
        center = leftEyeCenter + params.pupilOffset
    )
    drawCircle(
        color = Color.Black,
        radius = pupilRadius,
        center = rightEyeCenter + params.pupilOffset
    )
}