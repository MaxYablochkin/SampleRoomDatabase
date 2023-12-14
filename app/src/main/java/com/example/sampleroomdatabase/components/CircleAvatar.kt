package com.example.sampleroomdatabase.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun CircleAvatar(firstName: String, lastName: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        when {
            firstName.isEmpty() -> Text(
                text = lastName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            lastName.isEmpty() -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            else -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}