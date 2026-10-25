package com.oolyvi.yekun.ui.about

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.top_bars.MainTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.more.composables.MoreCard
import com.oolyvi.yekun.utils.Constants.URL_DISCORD
import com.oolyvi.yekun.utils.Constants.URL_INSTAGRAM

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutScreen(
    navActionManager: NavActionManager
) {

    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current



    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding(),
            topBar = {
                MainTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    topAppBarType = TopAppBarType.ABOUT,
                    context = context,
                    selectedRankTitle = null,
                    usernameString = null,
                    mediaDetailId = null,
                    showProfileShareDialog = { }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // discord
                MoreCard(
                    title = stringResource(R.string.discord),
                    subtitle = null,
                    contentDescription = stringResource(id = R.string.cd_discord_icon),
                    icon = R.drawable.discord,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val intent: Intent =
                            Uri
                                .parse(URL_DISCORD)
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            startActivity(context, intent, null)
                        } catch (e: ActivityNotFoundException) {

                        }
                    }
                )

                // instagram
                MoreCard(
                    title = stringResource(R.string.instagram),
                    subtitle = null,
                    contentDescription = stringResource(id = R.string.cd_instagram_icon),
                    icon = R.drawable.instagram,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val intent: Intent =
                            Uri
                                .parse(URL_INSTAGRAM)
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            startActivity(context, intent, null)
                        } catch (e: ActivityNotFoundException) {

                        }
                    }
                )

                // version
                MoreCard(
                    title = stringResource(R.string.version),
                    subtitle = stringResource(R.string.app_version_number),
                    contentDescription = stringResource(id = R.string.cd_version_icon),
                    icon = R.drawable.version,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Light,
                    onClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.app_version_number),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

                // credits
                MoreCard(
                    title = stringResource(R.string.credits),
                    subtitle = stringResource(R.string.some_collaborations),
                    contentDescription = stringResource(id = R.string.cd_people_icon),
                    icon = R.drawable.people,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Light,
                    onClick = {
                        navActionManager.toCredits()
                    }
                )

                // developed by
                MoreCard(
                    title = stringResource(R.string.developed_by),
                    subtitle = stringResource(R.string.oolyvi),
                    contentDescription = stringResource(id = R.string.cd_code_icon),
                    icon = R.drawable.code,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Light,
                    onClick = {
                        Toast.makeText(
                            context,
                            "(ﾐㅇ ༝ ㅇﾐ)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

            }
        }

    }

}