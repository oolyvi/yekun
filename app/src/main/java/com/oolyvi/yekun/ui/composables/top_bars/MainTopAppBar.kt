package com.oolyvi.yekun.ui.composables.top_bars

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomButton
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.utils.Constants

/**
 * top app bar util with TopAppBar type
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navActionManager: NavActionManager,
    topAppBarType: TopAppBarType,
    context: Context,
    selectedRankTitle: String?,
    usernameString: String?,
    mediaDetailId: Int?,
    showProfileShareDialog: (Boolean) -> Unit
) {

    /**
     * selected ranked title on AnimeRanking screen
     */
    val selectedRankAnime = when (selectedRankTitle) {
        "all" -> stringResource(R.string.top_all)
        "airing" -> stringResource(R.string.top_airings)
        "upcoming" -> stringResource(R.string.top_upcomings)
        "tv" -> stringResource(R.string.top_tvs)
        "ova" -> stringResource(R.string.top_ovas)
        "movie" -> stringResource(R.string.top_movies)
        "special" -> stringResource(R.string.top_specials)
        "bypopularity" -> stringResource(R.string.top_populars)
        "favorite" -> stringResource(R.string.top_favorites)
        else -> R.string.unknown
    }

    /**
     * selected ranked title on MangaRanking screen
     */
    val selectedRankManga = when (selectedRankTitle) {
        "all" -> stringResource(id = R.string.top_all)
        "manga" -> stringResource(R.string.top_mangas)
        "novels" -> stringResource(R.string.top_novels)
        "oneshots" -> stringResource(R.string.top_one_shots)
        "doujin" -> stringResource(R.string.top_doujins)
        "manhwa" -> stringResource(R.string.top_manhwas)
        "manhua" -> stringResource(R.string.top_manhuas)
        "bypopularity" -> stringResource(id = R.string.top_populars)
        "favorite" -> stringResource(id = R.string.top_favorites)
        else -> stringResource(id = R.string.unknown)
    }

    /**
     * top bar title, if there is
     */
    val topBarTitle = when (topAppBarType) {
        TopAppBarType.ABOUT -> stringResource(id = R.string.about)
        TopAppBarType.ANIME_RANKING -> selectedRankAnime
        TopAppBarType.MANGA_RANKING -> selectedRankManga
        TopAppBarType.CREDITS -> stringResource(id = R.string.credits)
        TopAppBarType.SETTINGS -> stringResource(id = R.string.settings)
        else -> {
            ""
        }
    }



    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Text(
                text = topBarTitle.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            if (topAppBarType == TopAppBarType.MORE) {
                Text(
                    text = stringResource(R.string.more),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    maxLines = 2
                )
            }

        },
        navigationIcon = {
            /**
             * show arrow back icon except More top bar
             */
            if (topAppBarType != TopAppBarType.MORE) {
                CustomButton(
                    icon = R.drawable.arrow_back,
                    iconSize = 25.dp,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.cd_back_icon),
                    isIconButton = true,
                    onClick = navActionManager::toBack
                )
            }
        },
        actions = {
            /**
             * actions for AnimeDetail, MangaDetail, Profile with if statements
             */
            if (topAppBarType == TopAppBarType.MANGA_DETAIL || topAppBarType == TopAppBarType.ANIME_DETAIL
                || topAppBarType == TopAppBarType.PROFILE
            ) {

                /**
                 * sharing url
                 */
                val sharingUrl = if (topAppBarType == TopAppBarType.MANGA_DETAIL) {
                    "${Constants.URL_MAL_MANGA}${mediaDetailId}"
                } else if (topAppBarType == TopAppBarType.ANIME_DETAIL) {
                    "${Constants.URL_MAL_ANIME}${mediaDetailId}"
                } else {
                    "${Constants.URL_MAL_PROFILE}${usernameString}"
                }

                Row {
                    // open media in browser
                    CustomButton(
                        icon = R.drawable.open_in_browser,
                        iconSize = 25.dp,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.cd_open_in_browser_icon),
                        isIconButton = true,
                        onClick = {
                            val sharingTitle: Intent =
                                Uri
                                    .parse(sharingUrl)
                                    .let { webpage ->
                                        Intent(Intent.ACTION_VIEW, webpage)
                                    }
                            try {
                                ContextCompat.startActivity(context, sharingTitle, null)
                            } catch (_: ActivityNotFoundException) {

                            }
                        }
                    )
                    // share media
                    CustomButton(
                        icon = R.drawable.share,
                        iconSize = 25.dp,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.cd_share_icon),
                        isIconButton = true,
                        onClick = {
                            if (topAppBarType == TopAppBarType.PROFILE) {
                                showProfileShareDialog(true)
                            } else {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, sharingUrl)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)

                                context.startActivity(shareIntent)
                            }
                        }
                    )
                }
            }

            if (topAppBarType == TopAppBarType.MORE) {
                CustomButton(
                    icon = R.drawable.gemini,
                    iconSize = 25.dp,
                    contentDescription = stringResource(R.string.cd_ai_icon),
                    isIconButton = false,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    onClick = navActionManager::toAi
                )
            }
        },
        scrollBehavior = scrollBehavior
    )

}