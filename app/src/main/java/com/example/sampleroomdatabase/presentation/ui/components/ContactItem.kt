package com.example.sampleroomdatabase.presentation.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.presentation.ContactViewModel

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContactItem(
    contact: Contact,
    onContactDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
) {
    val hapticFeedback = LocalHapticFeedback.current
    val defaultColorItemContact = MaterialTheme.colorScheme.surface
    val selectedColorItemContact = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)
    var colorItemContact by remember { mutableStateOf(defaultColorItemContact) }
    val animateColorItemContact by animateColorAsState(
        targetValue = when {
            !contactViewModel.selectedContacts.contains(contact) -> defaultColorItemContact
            colorItemContact == selectedColorItemContact -> selectedColorItemContact
            else -> defaultColorItemContact
        },
        animationSpec = spring(Spring.DampingRatioNoBouncy, Spring.StiffnessMediumLow),
        label = stringResource(R.string.animate_color_label)
    )

    ListItem(
        modifier = Modifier
            .padding(start = 60.dp, end = 20.dp)
            .clip(RoundedCornerShape(15.dp))
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onLongClickLabel = stringResource(R.string.add_contact_in_selected_list),
                onLongClick = {
                    colorItemContact = selectedColorItemContact
                    contactViewModel.selectedContacts.add(contact)
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                onClickLabel = stringResource(R.string.remove_contact_in_selected_list),
                onClick = {
                    colorItemContact = defaultColorItemContact
                    contactViewModel.selectedContacts.remove(contact)
                },
                onDoubleClick = { contact.id?.let { onContactDetail(it) } }
            )
            .then(modifier),
        leadingContent = { CircleAvatar(contact.firstName, contact.lastName, contact.avatarColor) },
        headlineContent = {
            Text(
                text = when {
                    contact.firstName.isBlank() -> contact.lastName
                    contact.lastName.isBlank() -> contact.firstName
                    else -> "${contact.firstName} ${contact.lastName}"
                }
            )
        },
        colors = ListItemDefaults.colors(animateColorItemContact)
    )
}