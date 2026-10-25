package com.oolyvi.yekun.ui.season_chart.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.season_chart.SeasonChartUiState

@Composable
fun SortByFilter(
    uiState: SeasonChartUiState,
    scrollToTop: () -> Unit,
    getFilteredAnimeSeasonChart: () -> Unit,
    onChangeSortType: (String?) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(start = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        uiState.sortTypeItemsList.forEach { item ->
            // if item is selected, it won't be selected again
            val isSelected = item == uiState.selectedSortType
            val label = uiState.sortItemLabel[item]?.let { stringResource(id = it) } ?: "All"

            FilterChip(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                selected = (item == uiState.selectedSortType),
                onClick = {
                    if (!isSelected) {
                        onChangeSortType(item)
                        getFilteredAnimeSeasonChart()
                        scrollToTop()
                    }
                },
                leadingIcon = {
                    val sortIcon = uiState.sortIconMap[item]
                    if (sortIcon != null) {
                        Icon(
                            painter = painterResource(id = sortIcon),
                            contentDescription = stringResource(R.string.cd_filter_icon),
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = label,
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