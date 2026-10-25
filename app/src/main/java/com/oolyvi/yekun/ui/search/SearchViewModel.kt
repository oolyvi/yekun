package com.oolyvi.yekun.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.repository.AnimeRepository
import com.oolyvi.yekun.data.repository.MangaRepository
import com.oolyvi.yekun.utils.GeneralMediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()


    /**
     * search for both android and manga according to searchType value
     */
    fun searchMedia(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val resultAnime = animeRepository.searchAnime(query = query)
                val resultManga = mangaRepository.searchManga(query = query)

                _uiState.update {
                    // when GeneralMedaType is Anime, it will search for anime
                    if (_uiState.value.searchType == GeneralMediaType.ANIME) {
                        it.copy(
                            animeSearchList = resultAnime.body()?.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    } else {
                        it.copy(
                            mangaSearchList = resultManga.body()?.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
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
     * on query changes
     */
    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(
                query = query
            )
        }
    }


    /**
     * clear search query
     */
    fun clearQuery() {
        _uiState.update {
            it.copy(
                query = ""
            )
        }
    }


    /**
     * on change search type
     */
    fun onChangeSearchType(value: GeneralMediaType) {
        _uiState.update {
            it.copy(
                searchType = value
            )
        }
    }


    /**
     * this will trigger search type value with icon toggle button
     */
    fun onCheckedSearchTypeValue(value: Boolean) {
        _uiState.update {
            it.copy(
                checkedValue = value
            )
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