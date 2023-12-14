package com.example.sampleroomdatabase.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.sampleroomdatabase.data.Contact

@Composable
internal fun DeleteContactButton(
    selectedContacts: List<Contact>,
    onClickDelete: (List<Contact>) -> Unit
) {
    IconButton(onClick = { onClickDelete(selectedContacts) }) {
        Icon(Icons.Default.Delete, "")
    }
}