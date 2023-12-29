package com.example.sampleroomdatabase.ui.theme

enum class ThemeSettings {
    LIGHT, DARK, SYSTEM;

    companion object {
        fun fromOrdinal(ordinal: Int) = values()[ordinal]
    }
}