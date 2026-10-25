package com.oolyvi.yekun.ui.anime_list.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.anime_list.AnimeListUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AnimeListDropdownFilter(
    scope: CoroutineScope,
    listState: LazyListState,
    uiState: AnimeListUiState,
    getFilteredUserAnimeList: () -> Unit,
    showDropdownMenu: () -> Unit,
    changeSort: (String?) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        /**
         * dropdown menu
         */
        DropdownMenu(
            expanded = uiState.showDropdownMenu,
            onDismissRequest = {
                showDropdownMenu()
            },
            offset = DpOffset(x = 20.dp, y = 40.dp)
        ) {
            // title
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.title),
                        color = if (uiState.selectedSort == "anime_title") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    if (uiState.selectedSort != "anime_title") {
                        changeSort("anime_title")
                        getFilteredUserAnimeList()
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.title_sort),
                        contentDescription = stringResource(R.string.cd_title_icon),
                        tint = if (uiState.selectedSort == "anime_title") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    if (uiState.selectedSort == "anime_title")
                        Icon(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = stringResource(R.string.cd_done_icon),
                            tint = MaterialTheme.colorScheme.primary
                        )
                }
            )

            // score
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.score_season_chart),
                        color = if (uiState.selectedSort == "list_score") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    if (uiState.selectedSort != "list_score") {
                        changeSort("list_score")
                        getFilteredUserAnimeList()
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.scoreboard),
                        contentDescription = stringResource(R.string.cd_scoreboard_icon),
                        tint = if (uiState.selectedSort == "list_score") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    if (uiState.selectedSort == "list_score")
                        Icon(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = stringResource(R.string.cd_done_icon),
                            tint = MaterialTheme.colorScheme.primary
                        )
                }
            )

            // last updated
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.last_updated),
                        color = if (uiState.selectedSort == "list_updated_at") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    if (uiState.selectedSort != "list_updated_at") {
                        changeSort("list_updated_at")
                        getFilteredUserAnimeList()
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.timelapse),
                        contentDescription = stringResource(R.string.cd_timelapse_icon),
                        tint = if (uiState.selectedSort == "list_updated_at") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    if (uiState.selectedSort == "list_updated_at")
                        Icon(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = stringResource(R.string.cd_done_icon),
                            tint = MaterialTheme.colorScheme.primary
                        )
                }
            )

            // start date
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.start_date),
                        color = if (uiState.selectedSort == "anime_start_date") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    if (uiState.selectedSort != "anime_start_date") {
                        changeSort("anime_start_date")
                        getFilteredUserAnimeList()
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.date_range),
                        contentDescription = stringResource(R.string.cd_date_range_icon),
                        tint = if (uiState.selectedSort == "anime_start_date") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    if (uiState.selectedSort == "anime_start_date")
                        Icon(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = stringResource(R.string.cd_done_icon),
                            tint = MaterialTheme.colorScheme.primary
                        )
                }
            )
        }
    }

}