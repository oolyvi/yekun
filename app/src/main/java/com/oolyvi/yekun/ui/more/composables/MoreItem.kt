package com.oolyvi.yekun.ui.more.composables

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.repository.LoginRepository
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.more.MoreUiState
import com.oolyvi.yekun.ui.theme.ColorRed
import com.oolyvi.yekun.ui.theme.ColorTitleNo
import com.oolyvi.yekun.ui.theme.ColorTitleYes
import com.oolyvi.yekun.utils.Constants
import com.oolyvi.yekun.utils.singleClick

@SuppressLint("IntentReset")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MoreItem(
    uiState: MoreUiState,
    isLoggedIn: Boolean,
    interactionSource: MutableInteractionSource,
    showPictureDialog: (Boolean) -> Unit,
    innerPadding: PaddingValues,
    navActionManager: NavActionManager,
    context: Context,
    scrollState: ScrollState,
    onLogOut: () -> Unit,
    onProfileClick: () -> Unit,
    showLogOutDialog: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * if user logged in
         */
        if (isLoggedIn && uiState.errorMessage.isNullOrEmpty()) {
            uiState.userStatistics?.let {
                uiState.userInfo?.let {
                    UserInfos(
                        uiState = uiState,
                        interactionSource = interactionSource,
                        showPictureDialog = showPictureDialog,
                        userInfo = it
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // profile button
                TextButton(
                    onClick = onProfileClick,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.profile),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }


        /**
         * this part will shown up always, doesn't matter user logged in or not
         */
        if (uiState.errorMessage.isNullOrEmpty() || !uiState.errorMessage.isNullOrEmpty()) {
            if (!isLoggedIn) {
                // login button
                MoreCard(
                    title = stringResource(R.string.login),
                    subtitle = stringResource(R.string.login_subtitle),
                    contentDescription = stringResource(id = R.string.cd_login_icon),
                    icon = R.drawable.login,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    iconTint = ColorTitleYes,
                    titleColor = ColorTitleYes,
                    subTitleColor = ColorTitleYes,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        startActivity(
                            context,
                            Intent(Intent.ACTION_VIEW, Uri.parse(LoginRepository.loginUrl)),
                            null
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // anime and manga news
            MoreCard(
                title = stringResource(R.string.anime_manga_news),
                subtitle = null,
                contentDescription = stringResource(id = R.string.cd_news_icon),
                icon = R.drawable.news,
                modifier = Modifier.padding(horizontal = 20.dp),
                iconTint = MaterialTheme.colorScheme.onSurface,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitleColor = MaterialTheme.colorScheme.onSurface,
                titleFontWeight = FontWeight.Bold,
                subTitleFontWeight = FontWeight.Normal,
                onClick = {
                    val sharingText: Intent =
                        Uri
                            .parse(Constants.URL_NEWS)
                            .let { webpage ->
                                Intent(Intent.ACTION_VIEW, webpage)
                            }
                    try {
                        startActivity(context, sharingText, null)
                    } catch (_: ActivityNotFoundException) {

                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // mal announcements
            MoreCard(
                title = stringResource(R.string.myanimelist_announcements),
                subtitle = null,
                contentDescription = stringResource(id = R.string.cd_announcement_icon),
                icon = R.drawable.announcement,
                modifier = Modifier.padding(horizontal = 20.dp),
                iconTint = MaterialTheme.colorScheme.onSurface,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitleColor = MaterialTheme.colorScheme.onSurface,
                titleFontWeight = FontWeight.Bold,
                subTitleFontWeight = FontWeight.Normal,
                onClick = {
                    val sharingText: Intent =
                        Uri
                            .parse(Constants.URL_ANNOUNCEMENT)
                            .let { webpage ->
                                Intent(Intent.ACTION_VIEW, webpage)
                            }
                    try {
                        startActivity(context, sharingText, null)
                    } catch (_: ActivityNotFoundException) {

                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // rate application
            MoreCard(
                title = stringResource(R.string.rate_app),
                subtitle = null,
                contentDescription = stringResource(id = R.string.cd_heart_icon),
                icon = R.drawable.heart,
                modifier = Modifier.padding(horizontal = 20.dp),
                iconTint = MaterialTheme.colorScheme.onSurface,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitleColor = MaterialTheme.colorScheme.onSurface,
                titleFontWeight = FontWeight.Bold,
                subTitleFontWeight = FontWeight.Normal,
                onClick = {
                    val intent: Intent =
                        Uri
                            .parse(Constants.URL_PLAY_STORE)
                            .let { webpage ->
                                Intent(Intent.ACTION_VIEW, webpage)
                            }
                    try {
                        startActivity(context, intent, null)
                    } catch (e: ActivityNotFoundException) {

                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // send feedback
            MoreCard(
                title = stringResource(R.string.send_feedback),
                subtitle = null,
                contentDescription = stringResource(id = R.string.cd_send_feedback_icon),
                icon = R.drawable.send_feedback,
                modifier = Modifier.padding(horizontal = 20.dp),
                iconTint = MaterialTheme.colorScheme.onSurface,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitleColor = MaterialTheme.colorScheme.onSurface,
                titleFontWeight = FontWeight.Bold,
                subTitleFontWeight = FontWeight.Normal,
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        type = "text/plain"
                        setData(Uri.parse("mailto:"))
                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("oolyvi@outlook.com")
                        )
                        putExtra(Intent.EXTRA_SUBJECT, "Yekun Feedback")
                    }
                    try {
                        startActivity(context, intent, null)
                    } catch (_: ActivityNotFoundException) {

                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // settings
            MoreCard(
                title = stringResource(R.string.settings),
                subtitle = null,
                contentDescription = stringResource(id = R.string.cd_settings_icon),
                icon = R.drawable.settings,
                modifier = Modifier.padding(horizontal = 20.dp),
                iconTint = MaterialTheme.colorScheme.onSurface,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitleColor = MaterialTheme.colorScheme.onSurface,
                titleFontWeight = FontWeight.Bold,
                subTitleFontWeight = FontWeight.Normal,
                onClick = {
                    navActionManager.toSettings()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // about
            MoreCard(
                title = stringResource(R.string.about),
                subtitle = null,
                contentDescription = stringResource(id = R.string.cd_about_icon),
                icon = R.drawable.about,
                modifier = Modifier.padding(horizontal = 20.dp),
                iconTint = MaterialTheme.colorScheme.onSurface,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitleColor = MaterialTheme.colorScheme.onSurface,
                titleFontWeight = FontWeight.Bold,
                subTitleFontWeight = FontWeight.Normal,
                onClick = {
                    navActionManager.toAbout()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))


            // log out shows when user logged in
            if (isLoggedIn) {
                MoreCard(
                    title = stringResource(R.string.log_out),
                    subtitle = null,
                    contentDescription = stringResource(id = R.string.cd_log_out_icon),
                    icon = R.drawable.logout,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    iconTint = ColorTitleNo,
                    titleColor = ColorTitleNo,
                    subTitleColor = ColorTitleNo,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        showLogOutDialog(true)
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }


    /**
     * log out dialog
     */
    if (uiState.showLogOutDialog) {
        AlertDialog(
            icon = {
                Text(
                    text = "༼☯﹏☯༽",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.log_out_subtitle),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.log_out_description),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Light
                )
            },
            onDismissRequest = {
                showLogOutDialog(false)
            },
            confirmButton = {
                TextButton(
                    onClick = singleClick {
                        onLogOut()
                        showLogOutDialog(false)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.yes),
                        color = ColorTitleYes
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = singleClick {
                        showLogOutDialog(false)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.no),
                        color = ColorRed
                    )
                }
            }
        )
    }

}

