package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.R
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
            text = { Text(text = stringResource(R.string.add_fake_contacts)) },
            onClick = { contactViewModel.preInitContacts() }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.delete_all_contacts)) },
            onClick = { contactViewModel.deleteAllContacts(contacts) }
        )
    }
}
