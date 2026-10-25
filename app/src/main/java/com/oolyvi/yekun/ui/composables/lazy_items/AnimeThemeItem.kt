package com.oolyvi.yekun.ui.composables.lazy_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.ui.theme.ColorPrimary

@Composable
fun AnimeThemeItem(
    text: String,
    onClick: () -> Unit
) {

    Text(
        text = text,
        modifier = Modifier
            .clickable(onClick = onClick),
        color = ColorPrimary
    )

    Spacer(modifier = Modifier.height(10.dp))

}