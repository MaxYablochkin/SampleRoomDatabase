package com.example.sampleroomdatabase

import com.example.sampleroomdatabase.ui.theme.ThemeSettings
import kotlinx.coroutines.flow.StateFlow

interface UserSettings {
    var theme: ThemeSettings
    val themeStream: StateFlow<ThemeSettings>
}