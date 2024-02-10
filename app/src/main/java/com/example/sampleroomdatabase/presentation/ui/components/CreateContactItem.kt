package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.navigation.Screens

@Composable
fun CreateContactItem(navController: NavController) {
    ListItem(
        modifier = Modifier
            .padding(start = 60.dp, end = 20.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable { navController.navigate(Screens.CreateContactScreen.destination) },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        },
        headlineContent = { Text(text = stringResource(R.string.btn_create_contact_in_list)) },
        colors = ListItemDefaults.colors(
            headlineColor = MaterialTheme.colorScheme.primary,
            leadingIconColor = MaterialTheme.colorScheme.primary,
        )
    )
}