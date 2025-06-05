package com.pyanov.liveanimation.designSystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalLAColors = staticCompositionLocalOf { LAColors() }

object LATheme {
    val colors: LAColors
        @Composable
        get() = LocalLAColors.current
}

@Composable
fun LATheme(
    content: @Composable () -> Unit
) {
    val localColors = LocalLAColors.current

    CompositionLocalProvider(
        LocalLAColors provides localColors,
    )
    {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = LATheme.colors.primary,
                primaryContainer = LATheme.colors.primary,
                onPrimary = LATheme.colors.white,
                onPrimaryContainer = LATheme.colors.white,
                secondary = LATheme.colors.secondary,
                secondaryContainer = LATheme.colors.secondary,
                onSecondary = LATheme.colors.text,
                onSecondaryContainer = LATheme.colors.text,

                background = LATheme.colors.background,
                onBackground = LATheme.colors.text,

                surface = LATheme.colors.white,
                surfaceTint = LATheme.colors.white,
                onSurface = LATheme.colors.text,

                outline = LATheme.colors.primary
            ),
            content = content
        )
    }
}