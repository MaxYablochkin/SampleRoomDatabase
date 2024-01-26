package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.sampleroomdatabase.data.database.Contact

@Composable
internal fun DeleteContactButton(
    contacts: List<Contact>,
    onClickDelete: (List<Contact>) -> Unit
) {
    IconButton(onClick = { onClickDelete(contacts) }) {
        Icon(Icons.Default.Delete, "")
    }
}