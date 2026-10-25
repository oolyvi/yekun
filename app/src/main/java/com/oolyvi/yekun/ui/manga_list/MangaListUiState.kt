package com.oolyvi.yekun.ui.manga_list

import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.manga.MangaUserList

@Immutable
data class MangaListUiState(
    val mangaUserList: List<MangaUserList.Data> = emptyList(),
    val sortedMangaUserList: List<MangaUserList.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedSort: String? = "manga_title",
    val selectedStatus: String? = "completed",
    val showBottomSheet: Boolean = false,
    val showDropdownMenu: Boolean = false
) {

    /**
     * this will helps to check filtering is active or not. After checking screen will handle
     * to shown up actual list or filtered list
     */
    fun isFilteringActive(uiState: MangaListUiState): Boolean {
        return uiState.selectedStatus != "completed" ||
                uiState.selectedSort != "manga_title"
    }

}