package com.oolyvi.yekun.ui.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
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
import com.oolyvi.yekun.ui.composables.lazy_items.ForYouItem
import com.oolyvi.yekun.ui.composables.top_bars.MainMediumTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.home.HomeViewModel
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalCoroutinesApi::class
)
@Composable
fun FullForYouScreen(
    navActionManager: NavActionManager
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    fun refresh() = scope.launch {
        viewModel.setLoading(true)

        viewModel.getFullForYouAnime()

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



    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .systemBarsPadding(),
        topBar = {
            MainMediumTopAppBar(
                scrollBehavior = scrollBehavior,
                navActionManager = navActionManager,
                topAppBarType = TopAppBarType.FULL_FOR_YOU,
                selectedSeason = null,
                selectedYear = null
            )
        }
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
                    if (uiState.errorMessage.isNullOrEmpty()) {
                        items(
                            uiState.animeForYouList,
                            key = { forYouAnime ->
                                forYouAnime.node?.id ?: 0
                            }
                        ) { forYouAnime ->
                            ForYouItem(
                                forYouAnime = forYouAnime,
                                onItemClick = {
                                    forYouAnime.node?.id?.let { it1 ->
                                        navActionManager.toAnimeDetails(
                                            it1
                                        )
                                    }
                                }
                            )
                        }
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

}