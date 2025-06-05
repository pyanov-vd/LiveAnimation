package com.pyanov.liveanimation.designSystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

private val White = Color(0xFFFFFFFF)
private val Black = Color(0xFF000000)
private val Background = Color(0xFFF6F7FD)
private val Primary = Color(0xFFD60064)
private val Secondary = Color(0xFFFD7EA9)
private val Body = Color(0xFF330524) //and body text color
private val Text = Color(0xFF330524) //and body text color
private val Disable = Color(0xFFD8D8D8)
private val MenuBg = Color(0xFFf7cbe0).copy(alpha = 0.9f)
private val Error = Color.Red

@Immutable
data class LAColors(
    val white: Color = Color.White,
    val black: Color = Black,
    val background: Color = Background,
    val primary: Color = Primary,
    val secondary: Color = Secondary,
    val body: Color = Body,
    val text: Color = Text,
    val disable: Color = Disable,
    val menuBg: Color = MenuBg,
    val error: Color = Error
)