package com.oolyvi.yekun.ui.settings.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.AppLanguage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LanguageDialog(
    showLanguageDialog: (Boolean) -> Unit,
    setLanguage: (AppLanguage) -> Unit,
    value: AppLanguage,
    interactionSource: MutableInteractionSource
) {

    AlertDialog(
        onDismissRequest = {
            showLanguageDialog(false)
        },
        title = {
            Text(
                text = stringResource(R.string.language),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AppLanguage.entriesLocalized.entries.toList()) { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    setLanguage(entry.key)
                                },
                                interactionSource = interactionSource,
                                indication = null
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = value == entry.key,
                            onClick = {
                                setLanguage(entry.key)
                            },
                            colors = RadioButtonColors(
                                selectedColor = MaterialTheme.colorScheme.onSurface,
                                unselectedColor = MaterialTheme.colorScheme.onSurface,
                                disabledSelectedColor = MaterialTheme.colorScheme.surface,
                                disabledUnselectedColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = stringResource(entry.value),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        confirmButton = { },
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
    )

}