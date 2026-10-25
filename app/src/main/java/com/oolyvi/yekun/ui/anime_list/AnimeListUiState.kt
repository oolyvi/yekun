package com.oolyvi.yekun.ui.anime_list

import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.anime.AnimeUserList

@Immutable
data class AnimeListUiState(
    val animeUserList: List<AnimeUserList.Data> = emptyList(),
    val sortedAnimeUserList: List<AnimeUserList.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedSort: String? = "anime_title",
    val selectedStatus: String? = "completed",
    val showBottomSheet: Boolean = false,
    val showDropdownMenu: Boolean = false
) {

    /**
     * this will helps to check filtering is active or not. After checking screen will handle
     * to shown up actual list or filtered list
     */
    fun isFilteringActive(uiState: AnimeListUiState): Boolean {
        return uiState.selectedStatus != "completed" ||
                uiState.selectedSort != "anime_title"
    }
}