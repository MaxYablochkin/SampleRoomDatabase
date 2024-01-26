package com.example.sampleroomdatabase.presentation.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.navigation.Screens
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.components.ContactItem
import com.example.sampleroomdatabase.presentation.ui.components.CreateContactItem
import com.example.sampleroomdatabase.presentation.ui.components.DeleteContactButton
import com.example.sampleroomdatabase.presentation.ui.components.ListHeader
import com.example.sampleroomdatabase.presentation.ui.theme.applyTonalElevation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    contacts: List<Contact>,
    onContactDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    val extendedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    val sortedContacts = contacts.sortedBy {
        if (it.firstName.isBlank()) {
            it.lastName.take(1).uppercase()
        } else {
            it.firstName.take(1).uppercase()
        }
    }

    val groupedContacts = sortedContacts.groupBy {
        if (it.firstName.isBlank()) {
            it.lastName.take(1).uppercase()
        } else {
            it.firstName.take(1).uppercase()
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .then(modifier),
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(contactViewModel.selectedContacts.size < 1) {
                        if (it) {
                            Text("Contacts")
                        } else {
                            Text("${contactViewModel.selectedContacts.size} selected")
                        }
                    }
                },
                navigationIcon = {
                    AnimatedContent(contactViewModel.selectedContacts.size < 1) {
                        if (it) {
                            null
                        } else {
                            IconButton(onClick = { contactViewModel.clearSelectedContacts() }) {
                                Icon(Icons.Default.Close, null)
                            }
                        }
                    }
                },
                actions = {
                    AnimatedContent(contactViewModel.selectedContacts.size < 1) {
                        if (it) {
                            null
                        } else {
                            DeleteContactButton(contacts) { listContact ->
                                contactViewModel.deleteSelectedContacts(listContact)
                            }
                        }
                    }
                    IconButton(onClick = { navController.navigate(Screens.SettingsScreen.destination) }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.applyTonalElevation(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        elevation = 0.dp
                    )
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screens.CreateContactScreen.destination) },
                icon = { Icon(Icons.Default.Add, "") },
                text = { Text("Add contact") },
                expanded = extendedFab,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item { CreateContactItem(navController) }
            groupedContacts.forEach { (letter, sortedGroupedContacts) ->
                stickyHeader { ListHeader(letter) }
                items(items = sortedGroupedContacts, key = { it.id!! }) { contact ->
                    ContactItem(contact, onContactDetail, Modifier.animateItemPlacement())
                }
            }
        }
    }
}