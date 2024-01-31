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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "")
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
                        Icon(Icons.Outlined.Edit, "")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Star, "")
                    }

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                    AnimatedVisibility(visible = expanded) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete") },
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
                                text = "Call",
                                icon = Icons.Outlined.Call,
                                contentDescription = "call"
                            )
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                text = "Text",
                                icon = Icons.AutoMirrored.Outlined.Message,
                                contentDescription = "message"
                            )
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                text = "Video",
                                icon = Icons.Outlined.VideoCameraFront,
                                contentDescription = "video"
                            )
                            if (it.email.isNotBlank()) {
                                ActionButton(
                                    modifier = Modifier.weight(1f),
                                    text = "Email",
                                    icon = Icons.Outlined.Mail,
                                    contentDescription = "email"
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
                        ContactInfoCard(nameCard = "Contact info") {
                            if (it.mobileNumber.isNotBlank()) {
                                ListItem(
                                    modifier = Modifier.clickable { },
                                    leadingContent = { Icon(Icons.Outlined.Phone, "") },
                                    headlineContent = { Text(it.mobileNumber) },
                                    supportingContent = { Text("Mobile") }
                                )
                            }
                            if (it.email.isNotBlank()) {
                                ListItem(
                                    modifier = Modifier.clickable { },
                                    leadingContent = { Icon(Icons.Outlined.Email, "") },
                                    headlineContent = { Text(it.email) },
                                    supportingContent = { Text("Email") }
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
                        ContactInfoCard(nameCard = "About ${it.firstName}") {
                            ListItem(
                                leadingContent = { Icon(Icons.Default.Business, "") },
                                headlineContent = { Text(it.companyName) },
                                supportingContent = { Text("Company") }
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