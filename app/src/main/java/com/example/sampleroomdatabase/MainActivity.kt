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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.sampleroomdatabase.data.settings.ThemeSettings
import com.example.sampleroomdatabase.data.settings.UserSettings
import com.example.sampleroomdatabase.navigation.AppNavHost
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.theme.SampleRoomDatabaseTheme
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
        installSplashScreen()
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

            /**
             * If dark mode is turned off, then turn off AMOLED colors.
             * This condition is external to all application screens,
             * in order to avoid updating the user interface on a specific screen.
             */
            if (!darkTheme) {
                userSettings.amoledColor = false
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
                        userSettings = userSettings,
                    )
                }
            }
        }
    }
}

