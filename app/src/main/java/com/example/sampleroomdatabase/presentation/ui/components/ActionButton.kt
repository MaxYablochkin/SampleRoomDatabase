package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    contentDescription: String? = null
) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { }
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        ActionButtonContent(text, icon, contentDescription)
    }
}

@Composable
fun ActionButtonContent(
    text: String,
    icon: ImageVector,
    contentDescription: String? = null
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(27.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}