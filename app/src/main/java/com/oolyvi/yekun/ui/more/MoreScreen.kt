package com.oolyvi.yekun.ui.more

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.oolyvi.yekun.ui.composables.top_bars.MainTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.MainBottomNavigation
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.more.composables.MoreItem
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalCoroutinesApi::class
)
@RequiresApi(Build.VERSION_CODES.N)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoreScreen(
    isLoggedIn: Boolean,
    navBackStackEntry: NavBackStackEntry,
    navActionManager: NavActionManager
) {

    val viewModel: MoreViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()

    fun refresh() = scope.launch {
        viewModel.setLoading(true)
        viewModel.getUserInfoAndStats()
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



    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding(),
            bottomBar = {
                MainBottomNavigation(
                    navBackStackEntry = navBackStackEntry,
                    navActionManager = navActionManager
                )
            },
            topBar = {
                MainTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    context = context,
                    topAppBarType = TopAppBarType.MORE,
                    selectedRankTitle = null,
                    usernameString = null,
                    mediaDetailId = null,
                    showProfileShareDialog = { }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                MoreItem(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    navActionManager = navActionManager,
                    interactionSource = interactionSource,
                    showPictureDialog = {
                        viewModel.showPictureDialog(it)
                    },
                    onLogOut = {
                        scope.launch {
                            viewModel.logOut()
                        }
                    },
                    showLogOutDialog = {
                        viewModel.showLogOutDialog(it)
                    },
                    onProfileClick = {
                        navActionManager.toProfile()
                    },
                    scrollState = scrollState,
                    context = context,
                    isLoggedIn = isLoggedIn
                )
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
                    .padding(top = 70.dp)
            )
        }

    }

}