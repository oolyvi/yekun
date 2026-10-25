package com.oolyvi.yekun.ui.composables.custom_buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay

/**
 * this button is combined of IconButton with Icon and Image to avoid multiple clicking on it
 */
@Composable
fun CustomButton(
    icon: Int,
    iconSize: Dp,
    contentDescription: String?,
    onClick: () -> Unit,
    isIconButton: Boolean,
    iconTint: Color
) {

    var enabled by remember { mutableStateOf(true) }


    LaunchedEffect(enabled) {
        if (enabled) return@LaunchedEffect
        else delay(1000L)
        enabled = true
    }



    IconButton(
        onClick = {
            onClick()
            enabled = false
        },
        enabled = enabled
    ) {
        if (isIconButton) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
        } else {
            Image(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize)
            )
        }
    }

}