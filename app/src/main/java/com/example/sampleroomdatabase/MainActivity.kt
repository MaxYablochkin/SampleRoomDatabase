package com.example.sampleroomdatabase

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.sampleroomdatabase.ui.theme.SampleRoomDatabaseTheme

class MainActivity : ComponentActivity() {
    private val contactViewModel: ContactViewModel by viewModels(factoryProducer = { ContactViewModel.factory })

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SampleRoomDatabaseTheme(
                dynamicColor = contactViewModel.dynamicColor,
                amoledColor = contactViewModel.amoledColor
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(contactViewModel)
                }
            }
        }
    }
}