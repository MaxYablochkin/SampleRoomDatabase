package com.example.sampleroomdatabase.ui.theme


enum class ThemeSettings {
    Light, Dark, System;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}