package com.oolyvi.yekun.ui.home

import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.anime.AnimeRanking
import com.oolyvi.yekun.data.model.anime.AnimeForYou
import com.oolyvi.yekun.data.model.manga.MangaRanking

@Immutable
data class HomeUiState(
    val animeForYouList: List<AnimeForYou.Data> = emptyList(),
    val topAnimeList: List<AnimeRanking.Data> = emptyList(),
    val topMangaList: List<MangaRanking.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)