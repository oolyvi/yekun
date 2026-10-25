package com.oolyvi.yekun.ui.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.season_chart.SeasonChartViewModel

@Composable
fun HomeHeader(
    navActionManager: NavActionManager
) {

    val viewModel: SeasonChartViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 30.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // season chart
        uiState.homeHeaderSeasonIcon?.let {
            HomeHeaderCard(
                cardIcon = it,
                contentDescription = stringResource(R.string.cd_season_chart_icon),
                cardTitle = stringResource(R.string.seasonal_chart),
                onClick = navActionManager::toSeasonChart
            )
        }

        // anime ranking
        HomeHeaderCard(
            cardIcon = R.drawable.anime,
            contentDescription = stringResource(R.string.cd_anime_icon),
            cardTitle = stringResource(R.string.anime_ranking),
            onClick = navActionManager::toAnimeRanking
        )

        // manga ranking
        HomeHeaderCard(
            cardIcon = R.drawable.manga,
            contentDescription = stringResource(R.string.cd_manga_icon),
            cardTitle = stringResource(R.string.manga_ranking),
            onClick = navActionManager::toMangaRanking
        )
    }

}