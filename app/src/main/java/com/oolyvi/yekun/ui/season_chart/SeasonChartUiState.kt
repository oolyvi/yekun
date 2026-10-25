package com.oolyvi.yekun.ui.season_chart

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeSeason
import java.time.Year
import java.util.Calendar
import java.util.Locale

@Immutable
data class SeasonChartUiState(
    val seasonList: List<AnimeSeason.Data> = emptyList(),
    val sortedSeasonList: List<AnimeSeason.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedSeason: String? = currentSeason,
    val defaultSeason: String? = currentSeason,
    val selectedYear: Int? = currentYear,
    val defaultYear: Int? = currentYear,
    val selectedSortType: String? = "anime_num_list_users",
    val showBottomSheet: Boolean = false,
    val seasonItemsList: List<String> = listOf("winter", "spring", "summer", "fall"),
    val sortTypeItemsList: List<String> = listOf("anime_num_list_users", "anime_score"),
    val yearsItemsList: List<Int> = (1917..currentYear).toList().sortedByDescending { it }
) {

    /**
     * get current year and month from calendar
     */
    companion object {
        private val calendar: Calendar by lazy {
            Calendar.getInstance(Locale.getDefault())
        }
        private val month = calendar[Calendar.MONTH]

        val currentSeason = when (month) {
            0, 1, 11 -> "winter"
            2, 3, 4 -> "spring"
            5, 6, 7 -> "summer"
            8, 9, 10 -> "fall"
            else -> "spring"
        }

        @SuppressLint("NewApi")
        val currentYear = Year.now().value
    }


    /**
     * this will helps to check filtering is active or not. After checking screen will handle
     * to shown up actual list or filtered list
     */
    fun isFilteringActive(uiState: SeasonChartUiState): Boolean {
        return uiState.selectedSeason != uiState.defaultSeason ||
                uiState.selectedYear != uiState.defaultYear ||
                uiState.selectedSortType != "anime_num_list_users"
    }


    val seasonIconMap = mapOf(
        "winter" to R.drawable.winter,
        "spring" to R.drawable.spring,
        "summer" to R.drawable.summer,
        "fall" to R.drawable.fall
    )
    val homeHeaderSeasonIcon = seasonIconMap[selectedSeason]
    val sortIconMap = mapOf(
        "anime_num_list_users" to R.drawable.members_count_sort,
        "anime_score" to R.drawable.score_sort
    )

    val sortItemLabel: Map<String, Int> = mapOf(
        "anime_num_list_users" to R.string.members_count,
        "anime_score" to R.string.score_season_chart
    )

}