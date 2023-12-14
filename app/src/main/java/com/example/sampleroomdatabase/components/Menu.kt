package com.example.sampleroomdatabase.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.Screens
import com.example.sampleroomdatabase.data.Contact

@Composable
internal fun DropdownMenu(
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
    contacts: List<Contact>,
    navController: NavController,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text(text = "Settings") },
            onClick = { navController.navigate(Screens.SettingsScreen.destination) }
        )
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
