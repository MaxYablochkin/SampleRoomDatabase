package com.example.sampleroomdatabase.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.UserSettings
import com.example.sampleroomdatabase.components.DialogSwitchTheme
import com.example.sampleroomdatabase.components.Setting
import com.example.sampleroomdatabase.ui.theme.ThemeSettings


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
    theme: ThemeSettings,
    dynamicColor: Boolean,
    amoledColor: Boolean,
    userSettings: UserSettings
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            var openDialog by remember { mutableStateOf(false) }

            val currentNameTheme = when (theme) {
                ThemeSettings.Light -> "Light"
                ThemeSettings.Dark -> "Dark"
                ThemeSettings.System -> "System default"
            }

            val darkTheme = when (theme) {
                ThemeSettings.Light -> false
                ThemeSettings.Dark -> true
                ThemeSettings.System -> isSystemInDarkTheme()
            }

            Setting(
                nameSetting = "Theme",
                primaryText = "Choose theme",
                secondaryText = currentNameTheme,
                /*secondaryName = theme.name,*/
                onClick = { openDialog = true }
            )

            if (openDialog) {
                DialogSwitchTheme(
                    onDismissRequest = { openDialog = false },
                    selectedTheme = theme,
                    onThemeSelected = {
                        userSettings.theme = it
                        openDialog = false
                    }
                )
            }

            /**
             * Dynamic color available from Android 12 and above.
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Setting(
                    nameSetting = "Color",
                    primaryText = "Dynamic color",
                    secondaryText = "Android 12+",
                    trailingContent = {
                        Switch(
                            checked = dynamicColor,
                            onCheckedChange = { userSettings.dynamicColor = !userSettings.dynamicColor }
                        )
                    },
                    onClick = {
                        userSettings.dynamicColor = !userSettings.dynamicColor
                    }
                )
            }

            /**
             * When dark mode is turned on, we show the AMOLED setting.
             * With dark mode turned off, hide the AMOLED setting and turn it off.
             */
            AnimatedVisibility(visible = darkTheme) {
                Setting(
                    primaryText = "AMOLED",
                    trailingContent = {
                        Switch(
                            checked = amoledColor,
                            onCheckedChange = {
                                userSettings.amoledColor = !userSettings.amoledColor
                            }
                        )
                    },
                    onClick = {
                        userSettings.amoledColor = !userSettings.amoledColor
                    }
                )
            }
        }
    }
}
