package com.oolyvi.yekun.ui.composables.custom_buttons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

/**
 * single click text itself to avoid unnecessary toast messages
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTextButton(
    title: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    maxLines: Int,
    overflow: TextOverflow,
    textColor: Color,
    onClick: () -> Unit
) {

    var enabled by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }


    LaunchedEffect(enabled) {
        if (enabled) return@LaunchedEffect
        else delay(5000L)
        enabled = true
    }



    Text(
        text = title,
        fontSize = fontSize,
        fontWeight = fontWeight,
        maxLines = maxLines,
        overflow = overflow,
        color = textColor,
        modifier = Modifier.combinedClickable(
            onClick = {
                if (enabled) {
                    onClick()
                    enabled = false
                }
            },
            interactionSource = interactionSource,
            indication = null
        )
    )

}