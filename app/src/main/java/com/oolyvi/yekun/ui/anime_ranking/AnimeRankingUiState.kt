package com.oolyvi.yekun.ui.anime_ranking

import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.anime.AnimeRanking

@Immutable
data class AnimeRankingUiState(
    val animeRankingList: List<AnimeRanking.Data> = emptyList(),
    val sortedAnimeRankingList: List<AnimeRanking.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedRankingType: String? = "all",
    val showBottomSheet: Boolean = false
) {

    /**
     * this will helps to check filtering is active or not. After checking screen will handle
     * to shown up actual list or filtered list
     */
    fun isFilteringActive(uiState: AnimeRankingUiState): Boolean {
        return uiState.selectedRankingType != "all"
    }

    /**
     * ranking items list
     */
    val itemList: List<String> = listOf(
        "all",
        "airing",
        "upcoming",
        "tv",
        "ova",
        "movie",
        "special",
        "bypopularity",
        "favorite"
    )

}