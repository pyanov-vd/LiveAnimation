package com.pyanov.liveanimation.draw.sun

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.CLOUD_HEIGHT
import com.pyanov.liveanimation.MOUNTAINS_ANIM_DURATION
import liveanimation.sharedliveanim.generated.resources.Res
import liveanimation.sharedliveanim.generated.resources.ill_cloud
import org.jetbrains.compose.resources.painterResource

@Composable
fun SunLayer(
    animateMountainLayers: Boolean,
) {
    BoxWithConstraints {
        val sunDiameterDp = 100.dp
        val sunInitialXOffsetDp = maxWidth
        val sunTargetXOffsetDp = (maxWidth * 2 / 3) - (sunDiameterDp / 2)

        val animatedSunXOffsetDp by animateDpAsState(
            targetValue = if (animateMountainLayers) sunTargetXOffsetDp else sunInitialXOffsetDp,
            animationSpec = tween(
                durationMillis = MOUNTAINS_ANIM_DURATION,
                easing = FastOutSlowInEasing
            ),
            label = "sunXOffset"
        )

        DrawSun(
            modifier = Modifier
                .size(sunDiameterDp)
                .align(Alignment.TopStart)
                .offset(
                    x = animatedSunXOffsetDp,
                    y = 50.dp
                )
        )

        val configuration = LocalWindowInfo.current
        val screenWidthDp = (configuration.containerSize.width * (2.2f / 4f)).toInt()

        Image(
            painter = painterResource(Res.drawable.ill_cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .size(CLOUD_HEIGHT.dp)
                .offset {
                    IntOffset(screenWidthDp, 150)
                },
            alpha = 0.97f,
            contentScale = ContentScale.FillWidth
        )
    }
}