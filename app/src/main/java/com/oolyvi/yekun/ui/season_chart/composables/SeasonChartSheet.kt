package com.oolyvi.yekun.ui.season_chart.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.season_chart.SeasonChartUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonChartSheet(
    scope: CoroutineScope,
    listState: LazyGridState,
    lazyListState: LazyListState,
    uiState: SeasonChartUiState,
    getFilteredAnimeSeasonChart: () -> Unit,
    onChangeSeason: (String?) -> Unit,
    onChangeYear: (Int?) -> Unit,
    onChangeSortType: (String?) -> Unit,
    showBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    bottomPadding: Dp
) {

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet(false)
        },
        shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
        sheetState = sheetState,
        windowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier.padding(bottom = 25.dp + bottomPadding)
        ) {
            // season
            Text(
                text = stringResource(R.string.season),
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                SeasonFilter(
                    uiState = uiState,
                    scrollToTop = {
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    },
                    getFilteredAnimeSeasonChart = getFilteredAnimeSeasonChart,
                    onChangeSeason = onChangeSeason
                )
            }


            // year
            Text(
                text = stringResource(R.string.year),
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
                    .align(Alignment.Start)
            )

            YearFilter(
                uiState = uiState,
                lazyListState = lazyListState,
                scrollToTop = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                },
                getFilteredAnimeSeasonChart = getFilteredAnimeSeasonChart,
                onChangeYear = onChangeYear
            )


            // sort by
            Text(
                text = stringResource(R.string.sort_by),
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
            )

            SortByFilter(
                uiState = uiState,
                scrollToTop = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                },
                getFilteredAnimeSeasonChart = getFilteredAnimeSeasonChart,
                onChangeSortType = onChangeSortType
            )
        }
    }

}