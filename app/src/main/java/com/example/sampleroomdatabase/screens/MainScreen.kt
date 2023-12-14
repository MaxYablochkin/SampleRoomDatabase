package com.example.sampleroomdatabase.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.Screens
import com.example.sampleroomdatabase.components.ContactItem
import com.example.sampleroomdatabase.components.DeleteContactButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    val contacts by contactViewModel.allContacts.collectAsState(initial = emptyList())
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val extendedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    if (!contactViewModel.showToolsTopAppBar && contactViewModel.selectedContacts.size < 1) {
                        Text("Contacts")
                    } else {
                        Text("${contactViewModel.selectedContacts.size} selected")
                    }
                },
                navigationIcon = {
                    if (!contactViewModel.showToolsTopAppBar && contactViewModel.selectedContacts.size < 1) {
                        null
                    } else {
                        IconButton(onClick = { contactViewModel.clearSelectedContacts() }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                },
                actions = {
                    if (!contactViewModel.showToolsTopAppBar && contactViewModel.selectedContacts.size < 1) {
                        null
                    } else {
                        DeleteContactButton(contacts) {
                            contactViewModel.deleteSelectedContacts(it)
                        }
                    }
                },
                scrollBehavior = scrollBehavior
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
            state = listState
        ) {
            items(contacts) { contact ->
                ContactItem(contact)
            }
        }
    }
}