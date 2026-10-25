package com.oolyvi.yekun.ui.season_chart.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.season_chart.SeasonChartUiState
import java.util.Locale

@Composable
fun SeasonFilter(
    uiState: SeasonChartUiState,
    scrollToTop: () -> Unit,
    getFilteredAnimeSeasonChart: () -> Unit,
    onChangeSeason: (String?) -> Unit,
) {
    val itemsInPairs = uiState.seasonItemsList.chunked(4)



    Column(
        modifier = Modifier.padding(start = 14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsInPairs.forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pair.forEach { item ->
                    // Check if the item is selected
                    val isSelected = item == uiState.selectedSeason

                    FilterChip(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        selected = (item == uiState.selectedSeason),
                        // Disable onClick action if the item is already selected
                        onClick = {
                            if (!isSelected)
                                onSeasonChipClicked(
                                    season = item,
                                    onChangeSeason = onChangeSeason,
                                    getFilteredAnimeSeasonChart = getFilteredAnimeSeasonChart,
                                    scrollToTop = scrollToTop
                                )
                        },
                        leadingIcon = {
                            val seasonIcon = uiState.seasonIconMap[item]
                            if (seasonIcon != null) {
                                Icon(
                                    painter = painterResource(id = seasonIcon),
                                    contentDescription = stringResource(R.string.cd_season_icon),
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(
                                    when (item) {
                                        "winter" -> R.string.winter
                                        "spring" -> R.string.spring
                                        "summer" -> R.string.summer
                                        "fall" -> R.string.fall
                                        else -> R.string.spring
                                    }
                                ).replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                },
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
    }
}


/**
 * this will avoid multiple clicks on season filter chip item
 */
private fun onSeasonChipClicked(
    season: String,
    onChangeSeason: (String?) -> Unit,
    getFilteredAnimeSeasonChart: () -> Unit,
    scrollToTop: () -> Unit
) {
    onChangeSeason(season)
    getFilteredAnimeSeasonChart()
    scrollToTop()
}
