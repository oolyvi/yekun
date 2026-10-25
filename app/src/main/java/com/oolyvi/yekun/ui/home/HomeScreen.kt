package com.oolyvi.yekun.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.oolyvi.yekun.App
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.top_bars.MainCenterAlignedTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.home.composables.ForYouView
import com.oolyvi.yekun.ui.home.composables.HomeFeedRow
import com.oolyvi.yekun.ui.home.composables.HomeHeader
import com.oolyvi.yekun.ui.home.composables.HomeSearchField
import com.oolyvi.yekun.ui.home.composables.TopAnimeView
import com.oolyvi.yekun.ui.home.composables.TopMangaView
import com.oolyvi.yekun.ui.main.navigation.MainBottomNavigation
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState",
    "RememberReturnType", "CoroutineCreationDuringComposition"
)
@Composable
fun HomeScreen(
    isLoggedIn: Boolean,
    navBackStackEntry: NavBackStackEntry,
    navActionManager: NavActionManager
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun refresh() = scope.launch {
        viewModel.setLoading(true)
        viewModel.getHomeItems()
        viewModel.setLoading(false)
    }

    val pullRefreshState = rememberPullRefreshState(uiState.isLoading, ::refresh)

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val (isConnectedBefore, setConnectedBefore) = remember { mutableStateOf(isConnected) }

    LaunchedEffect(isConnected) {
        // if the connection state changes from disconnected to connected, and it was previously disconnected, trigger refresh
        if (isConnected && !isConnectedBefore) {
            viewModel.getHomeItems()
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
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding(),
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
            topBar = {
                MainCenterAlignedTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    topAppBarType = TopAppBarType.HOME,
                    selectedStatus = null,
                    isLoading = null,
                    sortedUserListSize = null,
                    isLoggedIn = isLoggedIn,
                    onSortClick = { }
                )
            },
            bottomBar = {
                MainBottomNavigation(
                    navActionManager = navActionManager,
                    navBackStackEntry = navBackStackEntry
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    /**
                     * search field
                     */
                    Spacer(modifier = Modifier.height(20.dp))

                    HomeSearchField(
                        navActionManager = navActionManager
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    /**
                     * season chart, anime ranking, manga ranking
                     */
                    HomeHeader(
                        navActionManager = navActionManager
                    )

                    Spacer(modifier = Modifier.height(40.dp))


                    /**
                     * top anime
                     */
                    HomeFeedRow(
                        title = stringResource(R.string.top_anime),
                        paddingValues = PaddingValues(top = 10.dp),
                        onClick = navActionManager::toAnimeRanking
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    TopAnimeView(
                        navActionManager = navActionManager
                    )


                    /**
                     * top manga
                     */
                    HomeFeedRow(
                        title = stringResource(R.string.top_manga),
                        paddingValues = PaddingValues(top = 10.dp),
                        onClick = navActionManager::toMangaRanking
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    TopMangaView(
                        navActionManager = navActionManager
                    )


                    /**
                     * for you, this will shown up, when user is logged in
                     */
                    if (isLoggedIn) {
                        HomeFeedRow(
                            title = stringResource(R.string.for_you),
                            paddingValues = PaddingValues(top = 10.dp),
                            onClick = navActionManager::toFullRecommendations
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        ForYouView(
                            navActionManager = navActionManager
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                }
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

    }

}