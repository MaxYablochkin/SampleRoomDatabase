package com.example.sampleroomdatabase.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.components.ContactImageItem
import com.example.sampleroomdatabase.presentation.ui.components.DialogSavingContact
import com.example.sampleroomdatabase.presentation.ui.components.DropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactScreen(
    navController: NavController,
    contacts: List<Contact>,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showMore by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    val localContext = LocalContext.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_on_create_contact_screen),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (contactViewModel.firstName.isEmpty() && contactViewModel.lastName.isEmpty()) {
                                navController.popBackStack()
                            } else {
                                openDialog = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close_create_contact_screen)
                        )
                    }
                    if (openDialog) {
                        DialogSavingContact(
                            onDismissRequest = { openDialog = false },
                            popBackStack = { navController.popBackStack() }
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            contactViewModel.saveContact(
                                context = localContext,
                                popBackStack = { navController.popBackStack() }
                            )
                        }
                    ) {
                        Text(text = stringResource(R.string.save_contact))
                    }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }
                    AnimatedVisibility(expanded) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            contacts = contacts
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            contentPadding = PaddingValues(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            item { ContactImageItem() }

            item {
                OutlinedTextField(
                    value = contactViewModel.firstName,
                    onValueChange = { contactViewModel.firstName = it },
                    label = { Text(text = stringResource(R.string.first_name)) },
                    leadingIcon = { Icon(Icons.Outlined.Person, null) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = contactViewModel.lastName,
                    onValueChange = { contactViewModel.lastName = it },
                    label = { Text(text = stringResource(R.string.last_name)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                ListItem(
                    modifier = Modifier.clickable { showMore = !showMore },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.more_fields),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    },
                    trailingContent = {
                        AnimatedVisibility(
                            visible = showMore,
                            enter = slideInVertically(),
                            exit = slideOutVertically()
                        ) {
                            if (showMore) {
                                Icon(Icons.Default.KeyboardArrowDown, null)
                            } else {
                                Icon(Icons.Default.KeyboardArrowUp, null)
                            }
                        }
                    }
                )
            }

            item {
                AnimatedVisibility(
                    visible = showMore,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column {
                        OutlinedTextField(
                            value = contactViewModel.mobileNumber,
                            onValueChange = { contactViewModel.mobileNumber = it },
                            label = { Text(text = stringResource(R.string.mobile)) },
                            leadingIcon = { Icon(Icons.Outlined.Phone, null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        OutlinedTextField(
                            value = contactViewModel.email,
                            onValueChange = { contactViewModel.email = it },
                            label = { Text(text = stringResource(R.string.email)) },
                            leadingIcon = { Icon(Icons.Outlined.Email, null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        OutlinedTextField(
                            value = contactViewModel.companyName,
                            onValueChange = { contactViewModel.companyName = it },
                            label = { Text(text = stringResource(R.string.company)) },
                            leadingIcon = { Icon(Icons.Default.Business, null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
