package com.pyanov.liveanimation.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pyanov.liveanimation.AppContent
import com.pyanov.liveanimation.MountainAnim
import com.pyanov.liveanimation.designSystem.LATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { AppContent() }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    LATheme {
        MountainAnim(
            isAnimatedAlready = true,
            passwordPreviewVisibility = false
        )
    }
}
