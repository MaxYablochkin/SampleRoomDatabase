package com.example.sampleroomdatabase

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.ui.theme.DynamicColor
import com.example.sampleroomdatabase.ui.theme.SampleRoomDatabaseTheme
import com.example.sampleroomdatabase.ui.theme.dynamicColor

class MainActivity : ComponentActivity() {
    private val viewModel: ContactViewModel by viewModels(factoryProducer = { ContactViewModel.factory })

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            /*val viewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)*/

            SampleRoomDatabaseTheme(dynamicColor = viewModel.dynamicColor) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(viewModel)
                }
            }
        }
    }
}