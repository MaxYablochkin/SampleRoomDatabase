package com.example.sampleroomdatabase.presentation.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.navigation.Screens
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.components.ContactItem
import com.example.sampleroomdatabase.presentation.ui.components.CreateContactItem
import com.example.sampleroomdatabase.presentation.ui.components.DeleteContactButton
import com.example.sampleroomdatabase.presentation.ui.components.ListHeader
import com.example.sampleroomdatabase.presentation.ui.isScrollingUp

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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(R.drawable.new_ic_launcher_foreground),
                                    contentDescription = stringResource(R.string.image_on_the_top_appbar),
                                    modifier = Modifier.size(48.dp),
                                )
                                Text(text = stringResource(R.string.title_on_main_screen))
                            }
                        } else {
                            Text("${contactViewModel.selectedContacts.size} ${stringResource(R.string.selected_contacts)}")
                        }
                    }
                },
                navigationIcon = {
                    AnimatedContent(contactViewModel.selectedContacts.size < 1) {
                        if (it) {
                            null
                        } else {
                            IconButton(onClick = { contactViewModel.clearSelectedContacts() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.clear_selected_contacts)
                                )
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
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.go_to_the_settings_screen)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp)
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screens.CreateContactScreen.destination) },
                icon = { Icon(Icons.Default.Add, stringResource(R.string.go_to_the_create_contact_screen)) },
                text = { Text(text = stringResource(R.string.btn_create_contact)) },
                expanded = listState.isScrollingUp(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
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