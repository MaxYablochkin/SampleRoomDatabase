package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleroomdatabase.presentation.ContactViewModel

@Composable
internal fun DialogSavingContact(
    onDismissRequest: () -> Unit,
    popBackStack: () -> Unit,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    val localContext = LocalContext.current

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = AlertDialogDefaults.shape,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(modifier = Modifier.padding(DialogPadding)) {
                Text(
                    text = "Your changes have not been saved",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(ContentPadding)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            onDismissRequest()
                            popBackStack()
                        }
                    ) {
                        Text("Discard")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onDismissRequest()
                            contactViewModel.saveContact(
                                context = localContext,
                                popBackStack = { popBackStack() }
                            )
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

private val DialogPadding = PaddingValues(all = 24.dp)
private val ContentPadding = PaddingValues(bottom = 24.dp)