package com.example.sampleroomdatabase.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.data.Contact

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContactItem(
    contact: Contact,
    modifier: Modifier = Modifier,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
) {
    val defaultListItemColor = MaterialTheme.colorScheme.surface
    val selectedListItemColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)
    var colorItemContact by remember { mutableStateOf(defaultListItemColor) }

    ListItem(
        modifier = Modifier
            .padding(start = 60.dp, end = 20.dp)
            .clip(RoundedCornerShape(15.dp))
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onLongClick = {
                    colorItemContact = selectedListItemColor
                    contactViewModel.selectedContacts.add(contact)
                    contactViewModel.showToolsTopAppBar = true
                },
                onClick = {
                    colorItemContact = defaultListItemColor
                    contactViewModel.selectedContacts.remove(contact)
                    contactViewModel.showToolsTopAppBar = false
                }
            )
            .then(modifier),
        leadingContent = {
            CircleAvatar(
                firstName = contact.firstName.toString(),
                lastName = contact.lastName.toString(),
                avatarArgbColor = contact.avatarColor
            )
        },
        headlineContent = {
            Text(
                text = when {
                    contact.firstName.toString().isBlank() -> contact.lastName
                    contact.lastName.toString().isBlank() -> contact.firstName
                    else -> "${contact.firstName} ${contact.lastName}"
                }.toString()
            )
        },
        colors = ListItemDefaults.colors(colorItemContact)
    )
}