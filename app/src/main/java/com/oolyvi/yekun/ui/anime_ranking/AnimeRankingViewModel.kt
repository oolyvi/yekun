package com.oolyvi.yekun.ui.anime_ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeRankingViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimeRankingUiState())
    val uiState: StateFlow<AnimeRankingUiState> = _uiState.asStateFlow()


    init {
        getRankedAnime()
    }


    fun getRankedAnime(isFiltered: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val result =
                    animeRepository.getAnimeRanked(
                        rankingType = "all"
                    )
                _uiState.update {
                    if (isFiltered) {
                        it.copy(
                            sortedAnimeRankingList = result.body()?.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    } else {
                        it.copy(
                            animeRankingList = result.body()?.data ?: emptyList(),
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
     * set loading
     */
    fun setLoading(value: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = value
            )
        }
    }


    /**
     * on change ranking type
     */
    fun onChangeRankingType(value: String?) {
        _uiState.update {
            it.copy(
                selectedRankingType = value
            )
        }
    }


    /**
     * show bottom sheet
     */
    fun showBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(showBottomSheet = value)
        }
    }

}