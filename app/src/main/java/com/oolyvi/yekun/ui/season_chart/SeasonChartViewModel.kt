package com.oolyvi.yekun.ui.season_chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.repository.AnimeRepository
import com.oolyvi.yekun.ui.season_chart.SeasonChartUiState.Companion.currentSeason
import com.oolyvi.yekun.ui.season_chart.SeasonChartUiState.Companion.currentYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonChartViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonChartUiState())
    val uiState: StateFlow<SeasonChartUiState> = _uiState.asStateFlow()


    init {
        getSeasonChart()
    }


    fun getSeasonChart(isFiltered: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }

                val result =
                    animeRepository.getSeasonAnime(
                        year = currentYear,
                        season = currentSeason,
                        sort = "anime_num_list_users"
                    )
                val sortedResult =
                    _uiState.value.selectedSeason?.let {
                        _uiState.value.selectedYear?.let { it1 ->
                            _uiState.value.selectedSortType?.let { it2 ->
                                animeRepository.getSeasonAnime(
                                    year = it1,
                                    season = it,
                                    sort = it2
                                )
                            }
                        }
                    }

                _uiState.update {
                    if (isFiltered) {
                        it.copy(
                            sortedSeasonList = sortedResult?.body()?.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    } else {
                        it.copy(
                            seasonList = result.body()?.data ?: emptyList(),
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
     * on change season, year, sort type values
     */
    fun onChangeSeason(value: String?) {
        _uiState.update {
            it.copy(
                selectedSeason = value
            )
        }
    }

    fun onChangeYear(value: Int?) {
        _uiState.update {
            it.copy(
                selectedYear = value
            )
        }
    }

    fun onChangeSortType(value: String?) {
        _uiState.update {
            it.copy(
                selectedSortType = value
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