package com.example.sampleroomdatabase.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.components.SettingsWidget

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
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
            val isDark = isSystemInDarkTheme()
            if (isSystemInDarkTheme()) contactViewModel.dynamicOrAmoled(isDark) else contactViewModel.dynamicOrAmoled(isDark)

            SettingsWidget(
                nameSetting = "Theme",
                primaryName = "Choose theme",
                secondaryName = "System default",
            )
            SettingsWidget(
                primaryName = "Dynamic color",
                trailingContent = {
                    Switch(
                        checked = contactViewModel.dynamicColor,
                        onCheckedChange = {
                            contactViewModel.dynamicColor = !contactViewModel.dynamicColor
                            contactViewModel.dynamicOrAmoled(isDark)
                        },
                        enabled = contactViewModel.enabledSwitchDynamicColor
                    )
                },
                /*onClick = {
                    contactViewModel.dynamicColor = !contactViewModel.dynamicColor
                    contactViewModel.dynamicOrAmoled(isDark)
                }*/
            )
            SettingsWidget(
                primaryName = "AMOLED",
                trailingContent = {
                    Switch(
                        checked = contactViewModel.amoledColor,
                        onCheckedChange = {
                            contactViewModel.amoledColor = !contactViewModel.amoledColor
                            contactViewModel.dynamicOrAmoled(isDark)
                        },
                        enabled = contactViewModel.enabledSwitchAmoledColor
                    )
                },
                /*onClick = {
                    contactViewModel.dynamicColor = !contactViewModel.dynamicColor
                    contactViewModel.dynamicOrAmoled(isDark)
                }*/
            )
        }
    }
}