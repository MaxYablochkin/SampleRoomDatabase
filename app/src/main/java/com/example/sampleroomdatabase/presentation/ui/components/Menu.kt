package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.presentation.ContactViewModel

@Composable
internal fun DropdownMenu(
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
    contacts: List<Contact>,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Pre init contacts") },
            onClick = { contactViewModel.preInitContacts() }
        )
        DropdownMenuItem(
            text = { Text("Delete all contacts") },
            onClick = { contactViewModel.deleteAllContacts(contacts) }
        )
    }
}
