package com.example.sampleroomdatabase.presentation.ui.components

import kotlin.random.Random
import kotlin.random.nextInt

enum class CircleAvatarColors {
    Red,
    Orange,
    Yellow,
    Green,
    Nautical,
    Pink,
    Purple;

    fun getArgbColor() = when (this) {
        Red -> 0xFFEF5350
        Orange -> 0xFFFF7043
        Yellow -> 0xFFF9A825
        Green -> 0xFF558B2F
        Nautical -> 0xFF40C4FF
        Pink -> 0xFFFB40B0
        Purple -> 0xFFE040FB
    }
}

fun selectRandomArgbColor(): Long {
    val randomColor = Random.nextInt(0..6)
    return CircleAvatarColors.entries[randomColor].getArgbColor()
}