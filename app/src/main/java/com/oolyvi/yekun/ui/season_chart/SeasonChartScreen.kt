package com.oolyvi.yekun.ui.season_chart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolyvi.yekun.ui.composables.error_views.HomePartsErrorView
import com.oolyvi.yekun.ui.composables.fab_buttons.SeasonChartFab
import com.oolyvi.yekun.ui.composables.lazy_items.AnimeSeasonItem
import com.oolyvi.yekun.ui.composables.top_bars.MainMediumTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.season_chart.composables.SeasonChartSheet
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.connectivityState
import com.oolyvi.yekun.utils.isScrollingUpGrid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalCoroutinesApi::class
)
@Composable
fun SeasonChartScreen(
    navActionManager: NavActionManager
) {

    val viewModel: SeasonChartViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    val listState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    fun refresh() = scope.launch {
        viewModel.setLoading(true)

        if (uiState.isFilteringActive(uiState)) {
            viewModel.getSeasonChart(true)
        } else if (uiState.seasonList.size != uiState.sortedSeasonList.size) {
            viewModel.getSeasonChart(true)
        } else {
            viewModel.getSeasonChart()
        }

        viewModel.setLoading(false)
    }

    val pullRefreshState = rememberPullRefreshState(uiState.isLoading, ::refresh)

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val (isConnectedBefore, setConnectedBefore) = remember { mutableStateOf(isConnected) }

    LaunchedEffect(isConnected) {
        // if the connection state changes from disconnected to connected, and it was previously disconnected, trigger refresh
        if (isConnected && !isConnectedBefore) {
            refresh()
        }
        // update the isConnectedBefore state
        setConnectedBefore(isConnected)
    }

    /**
     * show snackbar message as an error message
     */
    if (!uiState.errorMessage.isNullOrEmpty()) {
        LaunchedEffect(uiState.errorMessage) {
            snackbarHostState.showSnackbar(uiState.errorMessage.toString())
        }
    }



    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            snackbarHost = {
                AnimatedVisibility(
                    visible = !uiState.errorMessage.isNullOrEmpty(),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    SnackbarHost(hostState = snackbarHostState) {
                        Snackbar(
                            containerColor = ColorDropped,
                            contentColor = ColorWhite
                        ) {
                            Text(
                                text = uiState.errorMessage.toString()
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding(),
            topBar = {
                MainMediumTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    topAppBarType = TopAppBarType.SEASON_CHART,
                    selectedSeason = uiState.selectedSeason,
                    selectedYear = uiState.selectedYear
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = listState.isScrollingUpGrid(),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    SeasonChartFab(
                        onClick = {
                            viewModel.showBottomSheet(it)
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                if (!uiState.isLoading && uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * actual view
                     */
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            end = 10.dp,
                            top = 10.dp,
                            bottom = 30.dp
                        )
                    ) {
                        val itemsToUse = if (uiState.isFilteringActive(uiState)) {
                            uiState.sortedSeasonList
                        } else {
                            uiState.seasonList
                        }

                        items(
                            itemsToUse,
                            key = { animeSeasonChart ->
                                animeSeasonChart.node?.id ?: 0
                            }
                        ) { seasonalAnime ->
                            AnimeSeasonItem(
                                animeSeason = seasonalAnime,
                                onItemClick = {
                                    seasonalAnime.node?.id?.let { it1 ->
                                        navActionManager.toAnimeDetails(it1)
                                    }
                                }
                            )
                        }
                    }
                } else if (!uiState.isLoading && !uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * error view
                     */
                    HomePartsErrorView()
                }
            } // box
        }
    }


    /**
     * pull refresh indicator
     */
    Box(modifier = Modifier.fillMaxSize()) {
        PullRefreshIndicator(
            refreshing = uiState.isLoading,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )
    }


    /**
     * bottom sheet filter
     */
    if (uiState.showBottomSheet) {
        SeasonChartSheet(
            scope = scope,
            listState = listState,
            uiState = uiState,
            lazyListState = lazyListState,
            getFilteredAnimeSeasonChart = {
                viewModel.getSeasonChart(true)
            },
            onChangeSeason = {
                viewModel.onChangeSeason(it)
            },
            onChangeYear = {
                viewModel.onChangeYear(it)
            },
            onChangeSortType = {
                viewModel.onChangeSortType(it)
            },
            showBottomSheet = {
                viewModel.showBottomSheet(it)
            },
            sheetState = sheetState,
            bottomPadding = WindowInsets.navigationBars.asPaddingValues()
                .calculateBottomPadding()
        )
    }

}