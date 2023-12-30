package com.example.sampleroomdatabase.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Setting(
    nameSetting: String? = null,
    primaryText: String? = null,
    secondaryText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    nameSetting?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 5.dp)
        )
    }
    ListItem(
        modifier = Modifier.clickable { onClick?.invoke() },
        headlineContent = { primaryText?.let { Text(it) } },
        supportingContent = { secondaryText?.let { Text(it) } },
        trailingContent = trailingContent
    )
}