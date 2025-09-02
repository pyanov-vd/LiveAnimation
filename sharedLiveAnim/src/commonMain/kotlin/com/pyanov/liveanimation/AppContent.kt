package com.pyanov.liveanimation

import androidx.compose.runtime.Composable
import com.pyanov.liveanimation.content.LoginScreen
import com.pyanov.liveanimation.designSystem.LATheme

@Composable
fun AppContent() {
    LATheme {
        LoginScreen()
    }
}