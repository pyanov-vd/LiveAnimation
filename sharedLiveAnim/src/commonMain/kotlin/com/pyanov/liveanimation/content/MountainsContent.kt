package com.pyanov.liveanimation.content

import androidx.compose.runtime.Composable
import com.pyanov.liveanimation.draw.mountains.MountainBack
import com.pyanov.liveanimation.draw.mountains.MountainFront
import com.pyanov.liveanimation.draw.mountains.MountainMiddle
import com.pyanov.liveanimation.draw.sun.SunLayer

@Composable
fun MountainsContent(
    pupilOffsetY: Float,
    animateMountainLayers: Boolean,
    isPasswordActuallyVisible: Boolean,
) {
    SunLayer(animateMountainLayers = animateMountainLayers)

    MountainBack(
        animateMountainLayers = animateMountainLayers,
        isPasswordActuallyVisible = isPasswordActuallyVisible,
        pupilOffsetY = pupilOffsetY
    )

    MountainMiddle(
        animateMountainLayers = animateMountainLayers,
        isPasswordActuallyVisible = isPasswordActuallyVisible,
        pupilOffsetY = pupilOffsetY
    )

    MountainFront(animateMountainLayers = animateMountainLayers)
}