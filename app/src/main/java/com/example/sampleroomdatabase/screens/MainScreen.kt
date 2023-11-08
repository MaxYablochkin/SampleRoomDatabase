package com.example.sampleroomdatabase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.ContactViewModel
import com.example.sampleroomdatabase.Screens
import com.example.sampleroomdatabase.data.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    val contacts by contactViewModel.allContacts.collectAsState(initial = emptyList())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()
    val extendedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Contacts") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screens.CreateContactScreen.destination) },
                icon = { Icon(Icons.Default.Add, "") },
                text = { Text("Add contact")},
                expanded = extendedFab
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
                ContactItem(contact) {
                    contactViewModel.deleteContact(it)
                }
            }
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    onClickDelete: (Contact) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { },
        leadingContent = { CircleAvatar(contact.firstName, contact.lastName) },
        headlineContent = { Text(text = "${contact.firstName} ${contact.lastName}") },
        trailingContent = {
            IconButton(onClick = { onClickDelete(contact) }) {
                Icon(Icons.Default.Delete, "")
            }
        }
    )
}

@Composable
private fun CircleAvatar(firstName: String, lastName: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        when {
            firstName.isEmpty() -> Text(
                text = lastName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            lastName.isEmpty() -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            else -> Text(
                text = firstName.take(1).uppercase(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}