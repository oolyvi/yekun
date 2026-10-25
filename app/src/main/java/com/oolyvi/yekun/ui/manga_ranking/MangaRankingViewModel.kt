package com.oolyvi.yekun.ui.manga_ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MangaRankingViewModel @Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MangaRankingUiState())
    val uiState: StateFlow<MangaRankingUiState> = _uiState.asStateFlow()


    init {
        getRankedManga()
    }


    fun getRankedManga(isFiltered: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val result =
                    mangaRepository.getRankedManga(
                        rankingType = "all"
                    )
                _uiState.update {
                    if (isFiltered) {
                        it.copy(
                            sortedMangaRankingList = result.body()?.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    } else {
                        it.copy(
                            mangaRankingList = result.body()?.data ?: emptyList(),
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
     * on change status and sort values
     */
    fun onChangeRankingType(value: String?) {
        _uiState.update {
            it.copy(
                selectedRankingType = value
            )
        }
    }


    /**
     * bottom sheet open and close
     */
    fun showBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(showBottomSheet = value)
        }
    }

}