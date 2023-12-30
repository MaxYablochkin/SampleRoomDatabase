package com.example.sampleroomdatabase

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.sampleroomdatabase.ui.theme.SampleRoomDatabaseTheme
import com.example.sampleroomdatabase.ui.theme.ThemeSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ContactViewModel by viewModels()

    @Inject
    lateinit var userSettings: UserSettings

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val theme = userSettings.themeStream.collectAsState()
            val dynamicColor = userSettings.dynamicColorStream.collectAsState()
            val amoledColor = userSettings.amoledColorStream.collectAsState()
            val darkTheme = when (theme.value) {
                ThemeSettings.Light -> false
                ThemeSettings.Dark -> true
                ThemeSettings.System -> isSystemInDarkTheme()
            }

            SampleRoomDatabaseTheme(
                darkTheme = darkTheme,
                dynamicColor = dynamicColor.value,
                amoledColor = amoledColor.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        theme = theme.value,
                        dynamicColor = dynamicColor.value,
                        amoledColor = amoledColor.value,
                        userSettings = userSettings
                    )
                }
            }
        }
    }
}

