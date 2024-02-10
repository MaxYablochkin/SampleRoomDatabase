package com.example.sampleroomdatabase.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.VideoCameraFront
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.components.ActionButton
import com.example.sampleroomdatabase.presentation.ui.components.CircleAvatar
import com.example.sampleroomdatabase.presentation.ui.components.ContactInfoCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactDetailScreen(
    navController: NavController,
    contact: Contact?,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    var expanded by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val showTitleTopAppBar by remember { derivedStateOf { listState.firstVisibleItemIndex >= 4 } }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.close_contact_detail_screen)
                        )
                    }
                },
                title = {
                    AnimatedVisibility(
                        visible = showTitleTopAppBar,
                        enter = fadeIn(animationSpec = tween(durationMillis = 350)),
                        exit = fadeOut(animationSpec = tween(durationMillis = 350))
                    ) {
                        contact?.let {
                            Text(
                                text = "${it.firstName} ${it.lastName}",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Edit, null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Star, null)
                    }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }
                    AnimatedVisibility(visible = expanded) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(R.string.delete_contact)) },
                                onClick = {
                                    contact?.let { contactViewModel.deleteContact(it) }
                                    expanded = false
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item {
                Spacer(Modifier.height(20.dp))
            }

            item {
                contact?.let {
                    CircleAvatar(
                        firstName = it.firstName,
                        lastName = it.lastName,
                        avatarArgbColor = it.avatarColor,
                        size = 180.dp,
                        fontSize = 100.sp
                    )
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
            }

            item {
                contact?.let {
                    Text(
                        text = when {
                            it.firstName.isBlank() -> it.lastName
                            it.lastName.isBlank() -> it.firstName
                            else -> "${it.firstName} ${it.lastName}"
                        },
                        fontSize = 28.sp
                    )
                }
            }

            item {
                contact?.let {
                    if (it.companyName.isNotBlank()) {
                        Spacer(Modifier.height(15.dp))
                        Text(it.companyName, fontSize = 14.sp)
                        Spacer(Modifier.height(15.dp))
                    }
                }
            }

            stickyHeader {
                contact?.let {
                    if (it.mobileNumber.isNotBlank()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                        ) {
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.btn_call),
                                icon = Icons.Outlined.Call,
                            )
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.btn_text),
                                icon = Icons.AutoMirrored.Outlined.Message,
                            )
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.btn_video),
                                icon = Icons.Outlined.VideoCameraFront,
                            )
                            if (it.email.isNotBlank()) {
                                ActionButton(
                                    modifier = Modifier.weight(1f),
                                    text = stringResource(R.string.btn_email),
                                    icon = Icons.Outlined.Mail,
                                )
                            }
                        }
                    }
                }
            }

            item {
                contact?.let {
                    if (it.mobileNumber.isNotBlank() || it.email.isNotBlank()) {
                        Spacer(Modifier.height(15.dp))
                        ContactInfoCard(nameCard = stringResource(R.string.contact_info)) {
                            if (it.mobileNumber.isNotBlank()) {
                                ListItem(
                                    modifier = Modifier.clickable { },
                                    leadingContent = { Icon(Icons.Outlined.Phone, null) },
                                    headlineContent = { Text(it.mobileNumber) },
                                    supportingContent = { Text(text = stringResource(R.string.mobile_info)) }
                                )
                            }
                            if (it.email.isNotBlank()) {
                                ListItem(
                                    modifier = Modifier.clickable { },
                                    leadingContent = { Icon(Icons.Outlined.Email, null) },
                                    headlineContent = { Text(it.email) },
                                    supportingContent = { Text(text = stringResource(R.string.email_info)) }
                                )
                            }
                        }
                    }
                }
            }

            item {
                contact?.let {
                    if (it.companyName.isNotBlank()) {
                        Spacer(Modifier.height(15.dp))
                        ContactInfoCard(nameCard = "${stringResource(R.string.about_person)} ${it.firstName}") {
                            ListItem(
                                leadingContent = { Icon(Icons.Default.Business, null) },
                                headlineContent = { Text(it.companyName) },
                                supportingContent = { Text(text = stringResource(R.string.company_info)) }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(850.dp))
            }
        }
    }
}