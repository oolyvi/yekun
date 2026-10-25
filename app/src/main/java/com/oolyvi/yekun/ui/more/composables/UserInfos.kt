package com.oolyvi.yekun.ui.more.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.user.UserInfo
import com.oolyvi.yekun.ui.more.MoreUiState

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun UserInfos(
    userInfo: UserInfo,
    uiState: MoreUiState,
    interactionSource: MutableInteractionSource,
    showPictureDialog: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userInfo.picture != null) {
            // user profile image
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userInfo.picture)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(130.dp)
                    .clickable {
                        showPictureDialog(true)
                    },
                contentDescription = stringResource(R.string.cd_profile_image),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .scale(0.5f),
                        color = MaterialTheme.colorScheme.surface,
                        trackColor = MaterialTheme.colorScheme.primary
                    )
                }
            )
        } else {
            // profile picture placeholder
            OutlinedIconButton(
                onClick = { },
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(130.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.cd_profile_placeholder),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(110.dp)
                )
            }
        }

    }


    /**
     * show profile picture dialog
     */
    if (uiState.showPictureDialog) {
        Dialog(
            onDismissRequest = {
                showPictureDialog(false)
            }
        ) {
            if (userInfo.picture != null) {
                SubcomposeAsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(userInfo.picture)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(330.dp)
                        .combinedClickable(
                            onClick = {
                                showPictureDialog(false)
                            },
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    contentDescription = stringResource(R.string.cd_profile_image),
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .scale(0.5f),
                            color = MaterialTheme.colorScheme.surface,
                            trackColor = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }
    }

}
