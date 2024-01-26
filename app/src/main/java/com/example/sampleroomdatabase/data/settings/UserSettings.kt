package com.example.sampleroomdatabase.data.settings

import kotlinx.coroutines.flow.StateFlow

interface UserSettings {
    var theme: ThemeSettings
    val themeStream: StateFlow<ThemeSettings>
    var dynamicColor: Boolean
    val dynamicColorStream: StateFlow<Boolean>
    var amoledColor: Boolean
    val amoledColorStream: StateFlow<Boolean>
}