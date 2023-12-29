package com.example.sampleroomdatabase

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.sampleroomdatabase.ui.theme.ThemeSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingsImpl @Inject constructor(@ApplicationContext context: Context) : UserSettings {
    override val themeStream: MutableStateFlow<ThemeSettings>
    override var theme: ThemeSettings by ThemePreferenceDelegate("SystemTheme", ThemeSettings.SYSTEM)
    private val preferences: SharedPreferences = context.getSharedPreferences("ContactTheme", Context.MODE_PRIVATE)

    init {
        themeStream = MutableStateFlow(theme)
    }

    inner class ThemePreferenceDelegate(
        private val name: String,
        private val default: ThemeSettings,
    ) : ReadWriteProperty<Any?, ThemeSettings> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): ThemeSettings {
            return ThemeSettings.fromOrdinal(preferences.getInt(name, default.ordinal))
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: ThemeSettings) {
            themeStream.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
        }
    }
}