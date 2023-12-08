package com.example.sampleroomdatabase.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sampleroomdatabase.ContactViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactScreen(
    navController: NavController,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    var showMore by remember { mutableStateOf(false) }
    var companyName by remember { mutableStateOf(TextFieldValue()) }
    var mobileNumber by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    val localContext = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create contact") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            if (contactViewModel.firstName.isEmpty() && contactViewModel.lastName.isEmpty()) {
                                Toast.makeText(localContext, "All fields empty", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                contactViewModel.insertContact()
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Text("Save")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            contentPadding = PaddingValues(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = contactViewModel.firstName,
                    onValueChange = { contactViewModel.firstName = it },
                    label = { Text("First name") },
                    leadingIcon = { Icon(Icons.Default.Person, "") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = contactViewModel.lastName,
                    onValueChange = { contactViewModel.lastName = it },
                    label = { Text("Last name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                ListItem(
                    modifier = Modifier.clickable { showMore = !showMore },
                    headlineContent = {
                        Text(
                            text = "More fields",
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingContent = {
                        if (showMore) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "",
                                modifier = Modifier.clickable { showMore = !showMore }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "",
                                modifier = Modifier.clickable { showMore = !showMore }
                            )
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
                            value = companyName,
                            onValueChange = { companyName = it },
                            label = { Text("Company") },
                            leadingIcon = { Icon(Icons.Default.Info, "") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = { mobileNumber = it },
                            label = { Text("Mobile") },
                            leadingIcon = { Icon(Icons.Default.Phone, "") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            leadingIcon = { Icon(Icons.Default.Email, "") },
                            suffix = { Text("@gmail.com") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
