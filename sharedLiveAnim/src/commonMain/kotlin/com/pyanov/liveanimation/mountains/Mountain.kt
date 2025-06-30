package com.pyanov.liveanimation.mountains

/**
 * Класс описывает гору. Каждая гора состоит из списка кривых Бизье
 */
data class Mountain(
    val mountains: List<MountainBezierCurve>
)

data class MountainBezierCurve(
    val x1: Float, val y1: Float,
    val x2: Float, val y2: Float,
    val x3: Float, val y3: Float
)

val BackMountains = listOf(
    Mountain(
        listOf(
            MountainBezierCurve(0.15f, 0.9f, 0.25f, 0.4f, 0.3f, 0.4f),
            MountainBezierCurve(0.35f, 0.4f, 0.45f, 0.7f, 0.5f, 0.8f)
        )
    ),
    Mountain(
        listOf(
            MountainBezierCurve(0.55f, 0.9f, 0.65f, 0.5f, 0.7f, 0.5f),
            MountainBezierCurve(0.75f, 0.5f, 0.85f, 0.7f, 1.0f, 0.8f)
        )
    ),
)

val MiddleMountains = listOf(
    Mountain(
        listOf(
            MountainBezierCurve(0.4f, 0.9f, 0.6f, 0.4f, 0.7f, 0.4f),
            MountainBezierCurve(0.8f, 0.4f, 0.95f, 0.7f, 1.0f, 0.8f)
        )
    ),
)

val FrontMountains = listOf(
    Mountain(
        listOf(
            MountainBezierCurve(0.05f, 0.7f, 0.15f, 0.4f, 0.3f, 0.4f),
            MountainBezierCurve(0.4f, 0.4f, 0.6f, 0.9f, 1.0f, 0.8f)
        )
    ),
)