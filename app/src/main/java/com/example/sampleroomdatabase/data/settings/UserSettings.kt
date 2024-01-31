package com.example.sampleroomdatabase.data.settings

import kotlinx.coroutines.flow.StateFlow

interface UserSettings {
    var theme: ThemeSettings
    val themeStateFlow: StateFlow<ThemeSettings>
    var dynamicColor: Boolean
    val dynamicColorStateFlow: StateFlow<Boolean>
    var amoledColor: Boolean
    val amoledColorStateFlow: StateFlow<Boolean>
}