package com.oolyvi.yekun.ui.ai.composables

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.ai.AiUiState
import com.oolyvi.yekun.ui.ai.ChatData
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomButton
import com.oolyvi.yekun.ui.theme.ColorTransparent
import com.oolyvi.yekun.utils.singleClick
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AiUserInput(
    bitmap: Bitmap?,
    uiState: AiUiState,
    updatePrompt: (String) -> Unit,
    addClick: () -> Unit,
    sendPrompt: () -> Unit
) {

    val iconButtonTint =
        if (uiState.prompt.isEmpty()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        else MaterialTheme.colorScheme.onSurface



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorTransparent)
            .padding(bottom = 10.dp, start = 4.dp, end = 4.dp, top = 5.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        // image, if user selects image from gallery
        bitmap?.let {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .combinedClickable(
                        onClick = {

                        },
                        onLongClick = {
                            ChatData.uriState.update { "" }
                        }
                    ),
                contentDescription = stringResource(R.string.cd_selected_chat_image),
                contentScale = ContentScale.Fit,
                bitmap = it.asImageBitmap()
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // text field
        TextField(
            value = uiState.prompt,
            maxLines = 10,
            modifier = Modifier
                .weight(1f),
            onValueChange = {
                updatePrompt(it)
            },
            leadingIcon = {
                CustomButton(
                    icon = R.drawable.add,
                    iconSize = 35.dp,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.cd_add_button),
                    isIconButton = true,
                    onClick = {
                        addClick()
                    }
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.prompt),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            },
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ColorTransparent,
                unfocusedIndicatorColor = ColorTransparent
            )
        )

        Spacer(modifier = Modifier.width(5.dp))

        // send button
        IconButton(
            onClick = singleClick {
                if (uiState.prompt.isNotBlank() && !uiState.isLoading) {
                    sendPrompt()
                    ChatData.uriState.update { "" }
                }
            },
            colors = IconButtonColors(
                containerColor = iconButtonTint,
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = iconButtonTint,
                disabledContentColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send),
                contentDescription = stringResource(R.string.cd_send_button),
                tint = MaterialTheme.colorScheme.surface
            )
        }
    }

}