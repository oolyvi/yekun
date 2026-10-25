package com.oolyvi.yekun.ui.search

import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.anime.AnimeList
import com.oolyvi.yekun.data.model.manga.MangaList
import com.oolyvi.yekun.utils.GeneralMediaType

@Immutable
data class SearchUiState(
    val animeSearchList: List<AnimeList.Data> = emptyList(),
    val mangaSearchList: List<MangaList.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val query: String = "",
    val checkedValue: Boolean? = false,
    val searchType: GeneralMediaType = GeneralMediaType.ANIME
)