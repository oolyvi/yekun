package com.oolyvi.yekun.ui.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomButton
import com.oolyvi.yekun.ui.composables.error_views.SearchErrorView
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.search.composables.MediaSearchView
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorTransparent
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.GeneralMediaType
import com.oolyvi.yekun.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun SearchScreen(
    navActionManager: NavActionManager
) {

    val viewModel: SearchViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.search_error_view
        )
    )

    fun refresh() = scope.launch {
        viewModel.setLoading(true)
        viewModel.searchMedia(uiState.query)
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

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
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
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
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
                .systemBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .systemBarsPadding()
                        .padding(
                            top = 8.dp,
                            start = 10.dp,
                            end = 15.dp
                        )
                        .fillMaxWidth()
                ) {
                    // text field and search type
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomButton(
                            icon = R.drawable.arrow_back,
                            iconSize = 25.dp,
                            iconTint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(R.string.cd_back_icon),
                            isIconButton = true,
                            onClick = navActionManager::toBack
                        )

                        OutlinedTextField(
                            shape = RoundedCornerShape(15.dp),
                            value = uiState.query,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .focusRequester(focusRequester),
                            onValueChange = {
                                viewModel.onQueryChanged(it)
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.search),
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            },
                            trailingIcon = {
                                if (uiState.query.isNotEmpty()) {
                                    CustomButton(
                                        icon = R.drawable.close,
                                        iconSize = 25.dp,
                                        iconTint = MaterialTheme.colorScheme.onSurface,
                                        contentDescription = stringResource(R.string.cd_close_icon),
                                        isIconButton = true,
                                        onClick = {
                                            viewModel.clearQuery()
                                        }
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    keyboardController?.hide()
                                    viewModel.searchMedia(uiState.query)
                                    scope.launch {
                                        listState.scrollToItem(0)
                                    }
                                }
                            ),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = ColorTransparent,
                                unfocusedContainerColor = ColorTransparent,
                                focusedIndicatorColor = ColorTransparent,
                                unfocusedIndicatorColor = ColorTransparent
                            )
                        )

                        uiState.checkedValue?.let {
                            IconToggleButton(
                                checked = it,
                                onCheckedChange = {
                                    viewModel.onCheckedSearchTypeValue(it)
                                    if (uiState.checkedValue!!) (viewModel.onChangeSearchType(
                                        GeneralMediaType.ANIME
                                    ))
                                    else (viewModel.onChangeSearchType(GeneralMediaType.MANGA))
                                    viewModel.searchMedia(uiState.query)
                                    scope.launch {
                                        listState.scrollToItem(0)
                                    }
                                }
                            ) {
                                if (uiState.searchType == GeneralMediaType.ANIME) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.anime),
                                        contentDescription = stringResource(id = R.string.cd_anime_icon),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(25.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.manga),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        contentDescription = stringResource(id = R.string.cd_manga_icon),
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                    }


                    /**
                     * when search list is not empty
                     */
                    if (uiState.errorMessage.isNullOrEmpty()) {
                        // searching for manga
                        if (uiState.mangaSearchList.isNotEmpty() && uiState.searchType == GeneralMediaType.MANGA) {
                            MediaSearchView(
                                listState = listState,
                                uiState = uiState,
                                mediaType = GeneralMediaType.MANGA,
                                navActionManager = navActionManager
                            )
                        }

                        // searching for anime
                        if (uiState.animeSearchList.isNotEmpty() && uiState.searchType == GeneralMediaType.ANIME) {
                            MediaSearchView(
                                listState = listState,
                                uiState = uiState,
                                mediaType = GeneralMediaType.ANIME,
                                navActionManager = navActionManager
                            )
                        }
                    }


                    /**
                     * error view
                     */
                    if ((uiState.mangaSearchList.isEmpty() || uiState.animeSearchList.isEmpty()) || !uiState.errorMessage.isNullOrEmpty()) {
                        lottieComposition?.let {
                            SearchErrorView(lottieComposition = it)
                        }
                    }

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
                .padding(top = 70.dp)
        )
    }

}