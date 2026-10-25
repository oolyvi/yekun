package com.oolyvi.yekun.ui.manga_detail.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomTextButton
import com.oolyvi.yekun.ui.theme.ColorStar

@Composable
fun MangaName(
    context: Context,
    clipboardManager: ClipboardManager,
    mangaDetail: MangaDetail,
) {

    val mediaType = when (mangaDetail.mediaType) {
        "unknown" -> stringResource(id = R.string.unknown)
        "manga" -> stringResource(id = R.string.manga)
        "novel" -> stringResource(id = R.string.novel)
        "one_shot" -> stringResource(id = R.string.one_shot)
        "doujinshi" -> stringResource(id = R.string.doujinshi)
        "manhwa" -> stringResource(id = R.string.manhwa)
        "manhua" -> stringResource(id = R.string.manhua)
        "oel" -> stringResource(id = R.string.oel)
        else -> {
            stringResource(id = R.string.unknown)
        }
    }
    val mangaStatus = when (mangaDetail.status) {
        "finished" -> stringResource(id = R.string.finished)
        "currently_publishing" -> stringResource(R.string.publishing)
        else -> stringResource(R.string.not_published_yet)
    }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // title
            if (!mangaDetail.title.isNullOrEmpty()) {
                CustomTextButton(
                    title = mangaDetail.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    onClick = {
                        clipboardManager.setText(AnnotatedString((mangaDetail.title)))
                        Toast.makeText(
                            context,
                            context.getString(R.string.manga_title_was_copied), Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                )
            } else {
                Text(
                    text = "-_-",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            /**
             * media type and status
             */
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // mediaType
                if (!mangaDetail.mediaType.isNullOrEmpty()) {
                    Text(
                        text = mediaType,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "·")
                Spacer(modifier = Modifier.width(10.dp))

                // status
                if (!mangaDetail.status.isNullOrEmpty()) {
                    Text(
                        text = mangaStatus,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        /**
         * mean
         */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = stringResource(R.string.cd_mean_score_icon),
                    tint = ColorStar
                )
                Spacer(modifier = Modifier.width(5.dp))
                if (mangaDetail.mean != null) {
                    Text(
                        text = "${mangaDetail.mean}",
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}