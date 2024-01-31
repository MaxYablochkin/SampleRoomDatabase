package com.example.sampleroomdatabase.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.sampleroomdatabase.data.settings.ThemeSettings

@Composable
internal fun DialogSwitchTheme(
    onDismissRequest: () -> Unit,
    selectedTheme: ThemeSettings,
    onThemeSelected: (ThemeSettings) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = AlertDialogDefaults.shape,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(modifier = Modifier.padding(DialogPadding)) {
                Text(
                    text = "Choose theme",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(TitlePadding)
                )
                RadioGroupSetting(
                    modifier = Modifier.padding(ContentPadding),
                    items = ThemeItem.themeItems,
                    selected = selectedTheme.ordinal,
                    onItemSelected = { onThemeSelected(ThemeSettings.fromOrdinal(it)) }
                )
                Row(Modifier.fillMaxWidth(), Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Cancel") }
                }
            }
        }
    }
}

@Composable
private fun RadioGroupSetting(
    modifier: Modifier = Modifier,
    items: List<ThemeItem>,
    selected: Int,
    onItemSelected: ((Int) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .selectableGroup()
            .then(modifier)
    ) {
        items.forEach { item ->
            RadioGroupItem(
                item = item,
                selected = selected == item.id,
                onClick = { onItemSelected?.invoke(item.id) }
            )
        }
    }
}

@Composable
private fun RadioGroupItem(
    item: ThemeItem,
    selected: Boolean,
    onClick: ((Int) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .selectable(
                selected = selected,
                onClick = { onClick?.invoke(item.id) },
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = item.title)
    }
}

data class ThemeItem(val id: Int, val title: String) {
    companion object {
        internal val themeItems = listOf(
            ThemeItem(id = ThemeSettings.Light.ordinal, title = "Light"),
            ThemeItem(id = ThemeSettings.Dark.ordinal, title = "Dark"),
            ThemeItem(id = ThemeSettings.System.ordinal, title = "System default")
        )
    }
}

private val DialogPadding = PaddingValues(all = 24.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val ContentPadding = PaddingValues(bottom = 24.dp)