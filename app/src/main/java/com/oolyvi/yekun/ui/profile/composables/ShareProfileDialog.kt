package com.oolyvi.yekun.ui.profile.composables

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.more.MoreUiState
import com.oolyvi.yekun.utils.Constants
import com.oolyvi.yekun.utils.rememberQrBitmapPainter

@Composable
fun ShareProfileDialog(
    uiState: MoreUiState,
    context: Context,
    showShareDialog: (Boolean) -> Unit
) {

    if (uiState.showShareDialog) {
        Dialog(
            onDismissRequest = {
                showShareDialog(false)
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // qr for sharing MAL profile
                Image(
                    painter = rememberQrBitmapPainter("${Constants.URL_MAL_PROFILE}${uiState.userInfo?.name}"),
                    contentDescription = stringResource(id = R.string.cd_share_qr),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(220.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            val sharingText =
                                "${Constants.URL_MAL_PROFILE}${uiState.userInfo?.name}"
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, sharingText)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)

                            context.startActivity(shareIntent)
                        },
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = stringResource(id = R.string.cd_share_icon),
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(30.dp)
                        )

                        Text(
                            text = stringResource(R.string.share_mal_profile),
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 20.sp
                        )
                    }

                }
            }
        }
    }

}