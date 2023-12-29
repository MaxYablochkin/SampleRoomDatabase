package com.example.sampleroomdatabase.screens

import android.content.SharedPreferences
import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.UserSettings
import com.example.sampleroomdatabase.components.DialogSwitchTheme
import com.example.sampleroomdatabase.components.Setting

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
        var openDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            Setting(
                nameSetting = "Theme",
                primaryName = "Choose theme",
                secondaryName = "System default",
                onClick = { openDialog = true }
            )
            Setting(
                primaryName = "Dynamic color",
                trailingContent = {
                    Switch(
                        checked = contactViewModel.dynamicColor,
                        onCheckedChange = {
                            contactViewModel.dynamicColor = !contactViewModel.dynamicColor
                        }
                    )
                }
            )

            /*if (openDialog) {
                DialogSwitchTheme(
                    onDismissRequest = { openDialog = false },
                    selectedTheme = theme.value,
                    onItemSelected = { userSettings.theme = it }
                )
            }*/

        }
    }
}
