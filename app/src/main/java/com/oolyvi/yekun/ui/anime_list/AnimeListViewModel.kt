package com.oolyvi.yekun.ui.anime_list

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
class AnimeListViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimeListUiState())
    val uiState: StateFlow<AnimeListUiState> = _uiState.asStateFlow()


    init {
        getUserAnimeList()
        getUserAnimeList(true)
    }


    fun getUserAnimeList(isFiltered: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val result =
                    _uiState.value.selectedStatus?.let {
                        _uiState.value.selectedSort?.let { it1 ->
                            animeRepository.getUserAnimeList(
                                sort = it1,
                                status = it
                            )
                        }
                    }
                if (result != null) {
                    _uiState.update {
                        if (isFiltered) {
                            it.copy(
                                sortedAnimeUserList = result.body()?.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        } else {
                            it.copy(
                                animeUserList = result.body()?.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        }
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
    fun onChangeStatus(value: String?) {
        _uiState.update {
            it.copy(
                selectedStatus = value
            )
        }
    }

    fun onChangeSort(value: String?) {
        _uiState.update {
            it.copy(
                selectedSort = value
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


    /**
     * show dropdown menu
     */
    fun showDropdownMenu(value: Boolean) {
        _uiState.update {
            it.copy(showDropdownMenu = value)
        }
    }

}