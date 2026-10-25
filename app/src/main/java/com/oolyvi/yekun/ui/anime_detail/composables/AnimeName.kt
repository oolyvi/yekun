package com.oolyvi.yekun.ui.anime_detail.composables

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
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomTextButton
import com.oolyvi.yekun.ui.theme.ColorStar

@Composable
fun AnimeName(
    context: Context,
    clipboardManager: ClipboardManager,
    animeDetail: AnimeDetail,
) {

    /**
     * media type mapping
     */
    val mediaType = when (animeDetail.mediaType) {
        "unknown" -> stringResource(id = R.string.unknown)
        "tv" -> stringResource(id = R.string.tv)
        "ova" -> stringResource(id = R.string.ova)
        "movie" -> stringResource(id = R.string.movie)
        "special" -> stringResource(id = R.string.special)
        "ona" -> stringResource(id = R.string.ona)
        "music" -> stringResource(id = R.string.music)
        else -> stringResource(id = R.string.unknown)
    }

    val animeStatus = when (animeDetail.status) {
        "finished_airing" -> stringResource(R.string.finished)
        "currently_airing" -> stringResource(R.string.airing)
        else -> stringResource(R.string.not_aired_yet)
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
            if (!animeDetail.title.isNullOrEmpty()) {
                CustomTextButton(
                    title = animeDetail.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    onClick = {
                        clipboardManager.setText(AnnotatedString((animeDetail.title)))
                        Toast.makeText(
                            context,
                            context.getString(R.string.anime_title_was_copied), Toast.LENGTH_SHORT
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
                if (!animeDetail.mediaType.isNullOrEmpty()) {
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
                if (!animeDetail.status.isNullOrEmpty()) {
                    Text(
                        text = animeStatus,
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
                if (animeDetail.mean != null) {
                    Text(
                        text = "${animeDetail.mean}",
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