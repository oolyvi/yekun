package com.oolyvi.yekun.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.repository.AnimeRepository
import com.oolyvi.yekun.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        getHomeItems()
    }


    fun getHomeItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }

                val topAnime =
                    animeRepository.getAnimeRanked(
                        rankingType = "all"
                    )
                val topManga =
                    mangaRepository.getRankedManga(
                        rankingType = "all"
                    )
                val animeForYou = animeRepository.getAnimeForYou()

                _uiState.update {
                    it.copy(
                        topAnimeList = topAnime.body()?.data ?: emptyList(),
                        topMangaList = topManga.body()?.data ?: emptyList(),
                        animeForYouList = animeForYou.body()?.data ?: emptyList(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage?.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }


    /**
     * get data on full for you screen
     */
    fun getFullForYouAnime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val response = animeRepository.getAnimeForYou()

                _uiState.update {
                    it.copy(
                        animeForYouList = response.body()?.data ?: emptyList(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage?.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }


    /**
     * set loading
     */
    fun setLoading(value: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = value
            )
        }
    }

}