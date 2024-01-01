package com.example.sampleroomdatabase.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
internal fun CircleAvatar(firstName: String, lastName: String) {

    val randomId = Random.nextInt(0..6)
    /*val backgroundColor = CircleAvatarColor.entries[randomId].getColor()*/

    val backgroundColor by rememberSaveable {
        lazy { CircleAvatarColor.entries[randomId].getColor() }
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        when {
            firstName.isEmpty() -> Text(
                text = lastName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background
            )

            lastName.isEmpty() -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background
            )

            else -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

internal enum class CircleAvatarColor {
    Red,
    Orange,
    Yellow,
    Green,
    Nautical,
    Pink,
    Purple;

    fun getColor(): Color {
        return when (this) {
            Red -> Color(0xFFEF5350)
            Orange -> Color(0xFFFF7043)
            Yellow -> Color(0xFFF9A825)
            Green -> Color(0xFF558B2F)
            Nautical -> Color(0xFF40C4FF)
            Pink -> Color(0xFFFB40B0)
            Purple -> Color(0xFFE040FB)
        }
    }
}