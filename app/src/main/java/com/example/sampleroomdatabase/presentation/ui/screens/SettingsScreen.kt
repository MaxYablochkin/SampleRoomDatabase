package com.example.sampleroomdatabase.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.data.settings.ThemeSettings
import com.example.sampleroomdatabase.data.settings.UserSettings
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.components.DialogSwitchTheme
import com.example.sampleroomdatabase.presentation.ui.components.Setting

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    theme: ThemeSettings,
    dynamicColor: Boolean,
    amoledColor: Boolean,
    userSettings: UserSettings,
    navController: NavController,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.title_on_settings_screen)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.close_settings_screen)
                        )
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
            val localHapticFeedback = LocalHapticFeedback.current
            var openDialog by rememberSaveable { mutableStateOf(false) }
            val themeNames =
                LocalContext.current.resources.getStringArray(R.array.secondary_text_theme)

            val currentNameTheme = when (theme) {
                ThemeSettings.Light -> themeNames[0]
                ThemeSettings.Dark -> themeNames[1]
                ThemeSettings.System -> themeNames[2]
            }

            val darkTheme = when (theme) {
                ThemeSettings.Light -> false
                ThemeSettings.Dark -> true
                ThemeSettings.System -> isSystemInDarkTheme()
            }

            Setting(
                nameSetting = stringResource(R.string.name_setting_theme),
                primaryText = stringResource(R.string.primary_text_theme),
                secondaryText = currentNameTheme,
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
            if (DynamicColorsAvailable) {
                Setting(
                    nameSetting = stringResource(R.string.name_setting_color),
                    primaryText = stringResource(R.string.primary_text_color),
                    secondaryText = stringResource(R.string.secondary_text_color),
                    onClick = {
                        userSettings.dynamicColor = !userSettings.dynamicColor
                        localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    trailingContent = {
                        Switch(
                            checked = dynamicColor,
                            onCheckedChange = {
                                userSettings.dynamicColor = !userSettings.dynamicColor
                                localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            },
                            colors = SwitchColors,
                            thumbContent = {
                                AnimatedVisibility(
                                    visible = dynamicColor,
                                    enter = slideInHorizontally(),
                                    exit = slideOutHorizontally()
                                ) {
                                    if (dynamicColor) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    } else null
                                }
                            }
                        )
                    }
                )
            }

            /**
             * When dark mode is turned on, we show the AMOLED setting.
             * With dark mode turned off, hide the AMOLED setting and turn it off.
             */
            AnimatedVisibility(visible = darkTheme) {
                Setting(
                    primaryText = stringResource(R.string.primary_text_amoled),
                    onClick = {
                        userSettings.amoledColor = !userSettings.amoledColor
                        localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    trailingContent = {
                        Switch(
                            checked = amoledColor,
                            onCheckedChange = {
                                userSettings.amoledColor = !userSettings.amoledColor
                                localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            },
                            colors = SwitchColors,
                            thumbContent = {
                                AnimatedVisibility(
                                    visible = amoledColor,
                                    enter = slideInHorizontally(),
                                    exit = slideOutHorizontally()
                                ) {
                                    if (amoledColor) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    } else null
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

internal val DynamicColorsAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

internal val SwitchColors
    @Composable
    get() = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colorScheme.primary,
        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        checkedIconColor = MaterialTheme.colorScheme.onPrimary
    )