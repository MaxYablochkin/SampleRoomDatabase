package com.example.sampleroomdatabase.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.data.Contact

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContactItem(
    contact: Contact,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
) {
    val defaultListItemColor = MaterialTheme.colorScheme.surface
    val selectedListItemColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)
    var colorItemContact by remember { mutableStateOf(defaultListItemColor) }

    ListItem(
        modifier = Modifier
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onLongClick = {
                    colorItemContact = selectedListItemColor
                    contactViewModel.showToolsTopAppBar = true
                    contactViewModel.selectedContacts.add(contact)
                },
                onClick = {
                    colorItemContact = defaultListItemColor
                    contactViewModel.showToolsTopAppBar = false
                    contactViewModel.selectedContacts.remove(contact)
                }
            ),
        leadingContent = { CircleAvatar(contact.firstName, contact.lastName, contact.avatarColor) },
        headlineContent = { Text("${contact.firstName} ${contact.lastName}") },
        colors = ListItemDefaults.colors(colorItemContact)
    )
}