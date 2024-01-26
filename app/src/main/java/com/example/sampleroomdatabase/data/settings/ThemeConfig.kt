package com.example.sampleroomdatabase.data.settings

enum class ThemeSettings {
    Light, Dark, System;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}