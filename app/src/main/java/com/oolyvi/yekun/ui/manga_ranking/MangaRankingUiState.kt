package com.oolyvi.yekun.ui.manga_ranking

import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.manga.MangaRanking

@Immutable
data class MangaRankingUiState(
    val mangaRankingList: List<MangaRanking.Data> = emptyList(),
    val sortedMangaRankingList: List<MangaRanking.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedRankingType: String? = "all",
    val showBottomSheet: Boolean = false
) {

    /**
     * this will helps to check filtering is active or not. After checking screen will handle
     * to shown up actual list or filtered list
     */
    fun isFilteringActive(uiState: MangaRankingUiState): Boolean {
        return uiState.selectedRankingType != "all"
    }

    /**
     * ranking items list
     */
    val itemList: List<String> = listOf(
        "all",
        "manga",
        "novels",
        "oneshots",
        "doujin",
        "manhwa",
        "manhua",
        "bypopularity",
        "favorite"
    )

}
