package com.oolyvi.yekun.ui.anime_list

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.anime_list.composables.AnimeListDropdownFilter
import com.oolyvi.yekun.ui.anime_list.composables.AnimeListSheet
import com.oolyvi.yekun.ui.composables.NotSignedScreen
import com.oolyvi.yekun.ui.composables.error_views.UserListErrorView
import com.oolyvi.yekun.ui.composables.fab_buttons.UserListFab
import com.oolyvi.yekun.ui.composables.lazy_items.UserMediaListItem
import com.oolyvi.yekun.ui.composables.top_bars.MainCenterAlignedTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.MainBottomNavigation
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.GeneralMediaType
import com.oolyvi.yekun.utils.connectivityState
import com.oolyvi.yekun.utils.isScrollingUpList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalCoroutinesApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnimeListScreen(
    isLoggedIn: Boolean,
    navBackStackEntry: NavBackStackEntry,
    navActionManager: NavActionManager
) {

    val viewModel: AnimeListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lottieCompositionErrorView by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.user_list_error_view
        )
    )
    val lottieCompositionNotSignedView by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.not_signed
        )
    )

    fun refresh() = scope.launch {
        viewModel.setLoading(true)

        if (uiState.isFilteringActive(uiState)) {
            viewModel.getUserAnimeList(true)
        } else if (uiState.animeUserList.size != uiState.sortedAnimeUserList.size) {
            viewModel.getUserAnimeList(true)
        } else {
            viewModel.getUserAnimeList()
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
            topBar = {
                MainCenterAlignedTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    topAppBarType = TopAppBarType.ANIME_LIST,
                    selectedStatus = uiState.selectedStatus,
                    isLoading = uiState.isLoading,
                    sortedUserListSize = uiState.sortedAnimeUserList.size,
                    isLoggedIn = isLoggedIn,
                    onSortClick = {
                        viewModel.showDropdownMenu(it)
                    }
                )
            },
            bottomBar = {
                MainBottomNavigation(
                    navBackStackEntry = navBackStackEntry,
                    navActionManager = navActionManager
                )
            },
            floatingActionButton = {
                if (isLoggedIn) {
                    AnimatedVisibility(
                        visible = listState.isScrollingUpList(),
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        UserListFab(
                            selectedStatusIcon = uiState.selectedStatus,
                            generalMediaType = GeneralMediaType.ANIME,
                            onClick = {
                                viewModel.showBottomSheet(it)
                            }
                        )
                    }
                }
            },
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                if (!uiState.isLoading && isLoggedIn && uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * actual view
                     */
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(
                            start = 15.dp,
                            end = 15.dp,
                            top = 10.dp,
                            bottom = 30.dp
                        )
                    ) {
                        val itemsToUse = if (uiState.isFilteringActive(uiState)) {
                            uiState.sortedAnimeUserList
                        } else {
                            uiState.animeUserList
                        }

                        items(
                            itemsToUse,
                            key = { userList ->
                                userList.node?.id ?: 0
                            }
                        ) { userAnimeList ->
                            UserMediaListItem(
                                userMedia = userAnimeList,
                                mediaType = GeneralMediaType.ANIME,
                                onItemClick = {
                                    userAnimeList.node?.id?.let { it1 ->
                                        navActionManager.toAnimeDetails(
                                            it1
                                        )
                                    }
                                }
                            )
                        }
                    }
                } else if (!uiState.isLoading && !uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * error view
                     */
                    lottieCompositionErrorView?.let {
                        UserListErrorView(lottieComposition = it)
                    }
                } else if (!isLoggedIn && uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * not signed screen
                     */
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        lottieCompositionNotSignedView?.let {
                            NotSignedScreen(
                                context = context,
                                lottieComposition = it
                            )
                        }
                    }
                }
            } // box
        }


        /**
         * loading
         */
        Box(modifier = Modifier.fillMaxSize()) {
            PullRefreshIndicator(
                refreshing = uiState.isLoading,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 70.dp)
            )
        }


        /**
         * dropdown menu filter
         */
        AnimeListDropdownFilter(
            scope = scope,
            listState = listState,
            uiState = uiState,
            getFilteredUserAnimeList = {
                viewModel.getUserAnimeList(true)
            },
            showDropdownMenu = {
                viewModel.showDropdownMenu(false)
            },
            changeSort = {
                viewModel.onChangeSort(it)
            }
        )


        /**
         * bottom sheet filter
         */
        if (uiState.showBottomSheet) {
            AnimeListSheet(
                scope = scope,
                listState = listState,
                uiState = uiState,
                getFilteredUserAnimeList = {
                    viewModel.getUserAnimeList(true)
                },
                onChangeStatus = {
                    viewModel.onChangeStatus(it)
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

}