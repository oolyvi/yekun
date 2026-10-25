package com.oolyvi.yekun.ui.season_chart.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.ui.season_chart.SeasonChartUiState

@Composable
fun YearFilter(
    uiState: SeasonChartUiState,
    lazyListState: LazyListState,
    scrollToTop: () -> Unit,
    getFilteredAnimeSeasonChart: () -> Unit,
    onChangeYear: (Int?) -> Unit
) {

    // this will scroll to selected year chip
    LaunchedEffect(key1 = true) {
        lazyListState.scrollToItem(uiState.yearsItemsList.indexOf(uiState.selectedYear))
    }



    LazyRow(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 14.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        items(uiState.yearsItemsList) { item ->
            // if item is selected, it won't be selected again
            val isSelected = item == uiState.selectedYear

            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp),
                selected = (item == uiState.selectedYear),
                onClick = {
                    if (!isSelected) {
                        onChangeYear(item)
                        getFilteredAnimeSeasonChart()
                        scrollToTop()
                    }
                },
                label = {
                    Text(
                        text = item.toString(),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = SelectableChipColors(
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface,

                    leadingIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.surface,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,

                    trailingIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTrailingIconColor = MaterialTheme.colorScheme.surface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,

                    containerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledSelectedContainerColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }

}