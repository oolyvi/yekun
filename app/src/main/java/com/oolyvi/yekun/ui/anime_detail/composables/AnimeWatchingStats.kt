package com.oolyvi.yekun.ui.anime_detail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.anime_detail.AnimeDetailUiState

@Composable
fun AnimeWatchingStats(
    animeWatchingStats: AnimeDetail.Statistics,
    uiState: AnimeDetailUiState,
    changeAnimationPlayed: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimeWatchingStatsChart(
            data = mapOf(
                Pair(
                    stringResource(id = R.string.watching),
                    animeWatchingStats.status?.watching ?: 0
                ),
                Pair(
                    stringResource(id = R.string.plan_to_watch),
                    animeWatchingStats.status?.planToWatch ?: 0
                ),
                Pair(
                    stringResource(id = R.string.completed),
                    animeWatchingStats.status?.completed ?: 0
                ),
                Pair(
                    stringResource(id = R.string.dropped),
                    animeWatchingStats.status?.dropped ?: 0
                ),
                Pair(stringResource(id = R.string.on_hold), animeWatchingStats.status?.onHold ?: 0),
            ),
            uiState = uiState,
            changeAnimationPlayed = changeAnimationPlayed
        )
    }

}