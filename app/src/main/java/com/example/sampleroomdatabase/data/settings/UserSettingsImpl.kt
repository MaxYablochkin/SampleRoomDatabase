package com.example.sampleroomdatabase.data.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class UserSettingsImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserSettings {

    override val themeStateFlow: MutableStateFlow<ThemeSettings>
    override var theme: ThemeSettings by ThemePreferenceDelegate("current_theme", ThemeSettings.System)

    override val dynamicColorStateFlow: MutableStateFlow<Boolean>
    override var dynamicColor: Boolean by DynamicColorPreferenceDelegate("dynamic_color", false)

    override val amoledColorStateFlow: MutableStateFlow<Boolean>
    override var amoledColor: Boolean by AmoledColorPreferenceDelegate("amoled_color", false)

    private val preferences: SharedPreferences = context.getSharedPreferences("contact_theme", Context.MODE_PRIVATE)

    init {
        themeStateFlow = MutableStateFlow(theme)
        dynamicColorStateFlow = MutableStateFlow(dynamicColor)
        amoledColorStateFlow = MutableStateFlow(amoledColor)
    }

    inner class ThemePreferenceDelegate(
        private val name: String,
        private val default: ThemeSettings,
    ) : ReadWriteProperty<Any?, ThemeSettings> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): ThemeSettings {
            return ThemeSettings.fromOrdinal(preferences.getInt(name, default.ordinal))
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: ThemeSettings) {
            themeStateFlow.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
        }
    }

    inner class DynamicColorPreferenceDelegate(
        private val name: String,
        private val default: Boolean
    ) : ReadWriteProperty<Any?, Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return preferences.getBoolean(name, default)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            dynamicColorStateFlow.value = value
            preferences.edit {
                putBoolean(name, value)
            }
        }
    }

    inner class AmoledColorPreferenceDelegate(
        private val name: String,
        private val default: Boolean
    ) : ReadWriteProperty<Any?, Boolean> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return preferences.getBoolean(name, default)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            amoledColorStateFlow.value = value
            preferences.edit {
                putBoolean(name, value)
            }
        }
    }
}
