package com.oolyvi.yekun.ui.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolyvi.yekun.ui.composables.ShimmerHome
import com.oolyvi.yekun.ui.composables.error_views.HomeErrorView
import com.oolyvi.yekun.ui.composables.lazy_items.ForYouItem
import com.oolyvi.yekun.ui.home.HomeViewModel
import com.oolyvi.yekun.ui.main.navigation.NavActionManager

@Composable
fun ForYouView(
    navActionManager: NavActionManager
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()



    if (!uiState.isLoading && uiState.errorMessage.isNullOrEmpty()) {
        /**
         * actual view
         */
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(start = 15.dp, end = 15.dp),
        ) {
            items(
                uiState.animeForYouList,
                key = { thisSeasonAnime ->
                    thisSeasonAnime.node?.id ?: 0
                }
            ) { forYouAnime ->
                ForYouItem(
                    forYouAnime = forYouAnime,
                    onItemClick = {
                        forYouAnime.node?.id?.let { it1 -> navActionManager.toAnimeDetails(it1) }
                    }
                )
            }
        }
    } else if (!uiState.isLoading && !uiState.errorMessage.isNullOrEmpty()) {
        /**
         * error view
         */
        HomeErrorView()
    } else if (uiState.isLoading) {
        /**
         * loading view
         */
        ShimmerHome()
    }

}