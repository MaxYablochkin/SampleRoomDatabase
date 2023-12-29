package com.example.sampleroomdatabase

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.sampleroomdatabase.components.DialogSwitchTheme
import com.example.sampleroomdatabase.ui.theme.SampleRoomDatabaseTheme
import com.example.sampleroomdatabase.ui.theme.ThemeSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ContactViewModel by viewModels(factoryProducer = { ContactViewModel.factory })

    @Inject
    lateinit var userSettings: UserSettings

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val theme = userSettings.themeStream.collectAsState()

            val darkTheme = when (theme.value) {
                ThemeSettings.LIGHT -> false
                ThemeSettings.DARK -> true
                ThemeSettings.SYSTEM -> isSystemInDarkTheme()
            }

            SampleRoomDatabaseTheme(darkTheme = darkTheme) {
                var openDialog by remember { mutableStateOf(false) }

                Button(onClick = { openDialog = true }, Modifier.padding(150.dp)) {
                    Text(text = "Open dialog")
                }
                if (openDialog) {
                    DialogSwitchTheme(
                        onDismissRequest = { openDialog = false },
                        selectedTheme = theme.value,
                        onThemeSelected = { userSettings.theme = it }
                    )
                }

               /* Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }*/
            }
        }
    }
}

