package com.example.sampleroomdatabase.presentation.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.fillMaxWidthInHorizontal() = this
    .fillMaxWidth()
    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))