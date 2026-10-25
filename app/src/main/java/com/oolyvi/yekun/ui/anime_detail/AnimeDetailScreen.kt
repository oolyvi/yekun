package com.oolyvi.yekun.ui.anime_detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.anime_detail.composables.AnimeDetailItem
import com.oolyvi.yekun.ui.composables.ShimmerDetail
import com.oolyvi.yekun.ui.composables.bottom_bars.DetailBottomBar
import com.oolyvi.yekun.ui.composables.error_views.DetailsErrorView
import com.oolyvi.yekun.ui.composables.top_bars.MainTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.edit_user_list.EditAnimeListSheet
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.GeneralMediaType
import com.oolyvi.yekun.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnimeDetailScreen(
    isLoggedIn: Boolean,
    navActionManager: NavActionManager
) {

    val viewModel: AnimeDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.details_error_view
        )
    )

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val (isConnectedBefore, setConnectedBefore) = remember { mutableStateOf(isConnected) }

    LaunchedEffect(isConnected) {
        // if the connection state changes from disconnected to connected, and it was previously disconnected, trigger refresh
        if (isConnected && !isConnectedBefore) {
            viewModel.getAnimeDetails()
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
                MainTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    context = context,
                    topAppBarType = TopAppBarType.ANIME_DETAIL,
                    selectedRankTitle = null,
                    usernameString = null,
                    mediaDetailId = uiState.animeDetail?.id,
                    showProfileShareDialog = { }
                )
            },
            bottomBar = {
                if (isLoggedIn && !uiState.isLoading && uiState.errorMessage.isNullOrEmpty()) {
                    uiState.animeDetail?.let {
                        DetailBottomBar(
                            generalMediaType = GeneralMediaType.ANIME,
                            selectedStatus = uiState.userAnimeListStatus?.status,
                            onClick = viewModel::showBottomSheet
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (!uiState.isLoading && uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * actual view
                     */
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        uiState.animeDetail?.let { animeDetail ->
                            AnimeDetailItem(
                                animeDetail = animeDetail,
                                context = context,
                                uiState = uiState,
                                interactionSource = interactionSource,
                                clipboardManager = clipboardManager,
                                showSynopsis = {
                                    viewModel.showSynopsis(it)
                                },
                                changeAnimationPlayed = {
                                    viewModel.onChangeAnimationPlayed(it)
                                },
                                showThemes = {
                                    viewModel.showThemes(it)
                                },
                                navActionManager = navActionManager
                            )
                        }
                    }
                } else if (!uiState.isLoading && !uiState.errorMessage.isNullOrEmpty()) {
                    /**
                     * error view
                     */
                    lottieComposition?.let {
                        DetailsErrorView(lottieComposition = it)
                    }
                } else if (uiState.isLoading) {
                    /**
                     * loading view
                     */
                    ShimmerDetail()
                }
            }
        }
    }


    /**
     * bottom sheet filter for user stats
     */
    if (uiState.showBottomSheet) {
        uiState.animeDetail?.let { animeDetail ->
            EditAnimeListSheet(
                animeDetail = animeDetail,
                myListStatus = uiState.userAnimeListStatus,
                onApply = {
                    viewModel.updateUserAnimeList()
                    viewModel.showBottomSheet(false)
                },
                onDelete = {
                    viewModel.deleteAnimeEntry()
                    viewModel.showBottomSheet(false)
                },
                showDeleteDialog = {
                    viewModel.showDeleteDialog(it)
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