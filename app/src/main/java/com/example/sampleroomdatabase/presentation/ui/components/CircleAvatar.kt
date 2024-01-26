package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun CircleAvatar(
    firstName: String,
    lastName: String,
    avatarArgbColor: Long,
    size: Dp = 40.dp,
    fontSize: TextUnit = 25.sp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(avatarArgbColor)),
        contentAlignment = Alignment.Center
    ) {
        when {
            firstName.isBlank() -> Text(
                text = lastName.take(1).uppercase(),
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.background
            )

            lastName.isBlank() -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.background
            )

            else -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}