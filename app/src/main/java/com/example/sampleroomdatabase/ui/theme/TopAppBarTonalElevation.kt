package com.example.sampleroomdatabase.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun ColorScheme.applyTonalElevation(backgroundColor: Color, elevation: Dp): Color {
    return when (backgroundColor) {
        surface -> surfaceColorAtElevation(elevation)
        else -> backgroundColor
    }
}