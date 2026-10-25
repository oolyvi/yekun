package com.oolyvi.yekun.ui.profile.composables

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.user.UserInfo
import com.oolyvi.yekun.data.model.user.UserStats
import com.oolyvi.yekun.ui.more.MoreUiState
import com.oolyvi.yekun.utils.Constants
import com.oolyvi.yekun.utils.singleClick

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProfileItem(
    userInfo: UserInfo,
    userStats: UserStats,
    uiState: MoreUiState,
    showPictureDialog: (Boolean) -> Unit,
    changeAnimationPlayed: (Boolean) -> Unit,
    context: Context
) {

    val birthdayValue =
        if (!userInfo.birthday.isNullOrEmpty()) "${uiState.formattedBirthday}" else "-_-"
    val locationValue =
        if (!userInfo.location.isNullOrEmpty()) "${userInfo.location}" else "-_-"
    val joinDateValue =
        if (!userInfo.joinedAt.isNullOrEmpty()) "${uiState.formattedJoinDate}" else "-_-"
    val interactionSource = remember { MutableInteractionSource() }



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.4f)
                    .padding(top = 40.dp)
            ) {
                // profile image
                if (userInfo.picture != null) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userInfo.picture)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 15.dp, bottom = 10.dp)
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
                    // profile image placeholder
                    OutlinedIconButton(
                        onClick = {},
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier
                            .padding(20.dp)
                            .clip(CircleShape)
                            .size(130.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = stringResource(R.string.cd_profile_placeholder),
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(110.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier
                    .padding(end = 15.dp)
                    .weight(0.6f)
            ) {
                // birthday
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cake),
                        contentDescription = stringResource(R.string.cd_cake_icon),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(25.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = birthdayValue,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        fontSize = 17.sp
                    )
                }

                // location
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = stringResource(R.string.cd_location_icon),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(25.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = locationValue,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 17.sp
                    )
                }

                // join date
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.date_range),
                        contentDescription = stringResource(R.string.cd_date_range_icon),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(25.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = joinDateValue,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        fontSize = 17.sp
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(40.dp))


        /**
         * edit button
         */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = singleClick {
                    val sharingText: Intent =
                        Uri
                            .parse(Constants.URL_EDIT_PROFILE)
                            .let { webpage ->
                                Intent(Intent.ACTION_VIEW, webpage)
                            }
                    try {
                        ContextCompat.startActivity(context, sharingText, null)
                    } catch (_: ActivityNotFoundException) {

                    }
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.open_in_new),
                        contentDescription = stringResource(id = R.string.cd_open_in_browser_icon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = stringResource(R.string.edit),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))


        /**
         * entry counts
         */
        Text(
            text = stringResource(R.string.total),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        UserEntryCounts(userStatistics = userStats)

        Spacer(modifier = Modifier.height(30.dp))

        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(0.4f)
        )

        Spacer(modifier = Modifier.height(30.dp))

        /**
         * anime stats
         */
        Text(
            text = stringResource(R.string.anime_stats),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        UserAnimeStats(
            userStatistics = userStats,
            uiState = uiState,
            changeAnimationPlayed = changeAnimationPlayed
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(0.4f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        /**
         * manga stats
         */
        Text(
            text = stringResource(R.string.manga_stats),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        UserMangaStats(
            userStatistics = userStats,
            uiState = uiState,
            changeAnimationPlayed = changeAnimationPlayed
        )

        Spacer(modifier = Modifier.height(50.dp))

    }


    /**
     * profile picture dialog
     */
    if (uiState.showPictureDialogFull) {
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