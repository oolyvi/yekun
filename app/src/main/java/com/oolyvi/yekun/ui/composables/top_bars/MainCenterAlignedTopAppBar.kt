package com.oolyvi.yekun.ui.composables.top_bars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.ai.ChatData
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomButton
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.theme.ColorAiTitle1
import com.oolyvi.yekun.ui.theme.ColorAiTitle2
import com.oolyvi.yekun.ui.theme.ColorAiTitle3
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCenterAlignedTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navActionManager: NavActionManager,
    topAppBarType: TopAppBarType,
    selectedStatus: String?,
    isLoading: Boolean?,
    sortedUserListSize: Int?,
    isLoggedIn: Boolean?,
    onSortClick: (Boolean) -> Unit
) {

    /**
     * Gemini AI title gradient on AI screen
     */
    val aiTopBarTitleGradient = listOf(
        ColorAiTitle1,
        ColorAiTitle2,
        ColorAiTitle3
    )

    /**
     * status title of anime
     */
    val statusTitleAnime = when (selectedStatus) {
        "watching" -> stringResource(id = R.string.watching)
        "completed" -> stringResource(id = R.string.completed)
        "on_hold" -> stringResource(id = R.string.on_hold)
        "dropped" -> stringResource(id = R.string.dropped)
        "plan_to_watch" -> stringResource(id = R.string.plan_to_watch)
        else -> stringResource(id = R.string.completed)
    }

    /**
     * status title of manga
     */
    val statusTitleManga = when (selectedStatus) {
        "reading" -> stringResource(id = R.string.reading)
        "completed" -> stringResource(id = R.string.completed)
        "on_hold" -> stringResource(id = R.string.on_hold)
        "dropped" -> stringResource(id = R.string.dropped)
        "plan_to_read" -> stringResource(id = R.string.plan_to_read)
        else -> stringResource(id = R.string.completed)
    }



    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            /**
             * ai title
             */
            if (topAppBarType == TopAppBarType.AI) {
                Text(
                    text = stringResource(id = R.string.gemini_ai),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = aiTopBarTitleGradient
                        )
                    )
                )
            }
        },
        navigationIcon = {
            /**
             * don't show back arrow on this screens
             */
            if (topAppBarType != TopAppBarType.HOME && topAppBarType != TopAppBarType.ANIME_LIST && topAppBarType != TopAppBarType.MANGA_LIST) {
                CustomButton(
                    icon = R.drawable.arrow_back,
                    iconSize = 25.dp,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.cd_back_icon),
                    isIconButton = true,
                    onClick = {
                        ChatData.uriState.update { "" }
                        navActionManager.toBack()
                    }
                )
            }

            if (topAppBarType == TopAppBarType.ANIME_LIST || topAppBarType == TopAppBarType.MANGA_LIST) {
                /**
                 * screen titles
                 */
                val userListScreenTitle = if (topAppBarType == TopAppBarType.ANIME_LIST) {
                    stringResource(R.string.anime_list)
                } else {
                    stringResource(R.string.manga_list)
                }

                /**
                 * selected status titles under screen titles
                 */
                val selectedStatusTitle = if (topAppBarType == TopAppBarType.ANIME_LIST) {
                    if (isLoading == true) stringResource(R.string.loading) else "$statusTitleAnime (${sortedUserListSize})"
                } else {
                    if (isLoading == true) stringResource(R.string.loading) else "$statusTitleManga (${sortedUserListSize})"
                }


                Column {
                    Text(
                        text = userListScreenTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )

                    if (isLoggedIn == true) {
                        selectedStatus?.let {
                            Text(
                                text = selectedStatusTitle,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Light,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            }

            /**
             * home
             */
            if (topAppBarType == TopAppBarType.HOME) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = stringResource(id = R.string.cd_app_icon),
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                }
            }
        },
        actions = {
            /**
             * actions for both anime ANIME_LIST & MANGA_LIST
             */
            if (topAppBarType == TopAppBarType.ANIME_LIST || topAppBarType == TopAppBarType.MANGA_LIST) {
                Row {
                    CustomButton(
                        icon = R.drawable.search,
                        iconSize = 25.dp,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        isIconButton = true,
                        contentDescription = stringResource(R.string.cd_search_icon),
                        onClick = navActionManager::toSearch
                    )
                    CustomButton(
                        icon = R.drawable.gemini,
                        iconSize = 25.dp,
                        contentDescription = stringResource(R.string.cd_ai_icon),
                        isIconButton = false,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        onClick = navActionManager::toAi
                    )
                    if (isLoggedIn == true) {
                        CustomButton(
                            icon = R.drawable.sort,
                            iconSize = 25.dp,
                            iconTint = MaterialTheme.colorScheme.onSurface,
                            isIconButton = true,
                            contentDescription = stringResource(R.string.cd_filter_icon),
                            onClick = {
                                onSortClick(true)
                            }
                        )
                    }
                }
            }

            /**
             * HOME actions
             */
            if (topAppBarType == TopAppBarType.HOME) {
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
        scrollBehavior = scrollBehavior,
    )

}