package com.pyanov.liveanimation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform