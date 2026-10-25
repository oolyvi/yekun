package com.oolyvi.yekun.ui.manga_detail.composables

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomButton
import com.oolyvi.yekun.ui.composables.error_views.MediaPicturesErrorView
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.manga_detail.MangaDetailViewModel
import com.oolyvi.yekun.ui.theme.ColorDarkGray
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorLightGray
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.ConnectionState
import com.oolyvi.yekun.utils.Constants.URL_MAL_MANGA
import com.oolyvi.yekun.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun MangaPicturesScreen(
    navActionManager: NavActionManager
) {

    val viewModel: MangaDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        uiState.mangaPicturesList.size
    })
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val (isConnectedBefore, setConnectedBefore) = remember { mutableStateOf(isConnected) }

    LaunchedEffect(isConnected) {
        // if the connection state changes from disconnected to connected, and it was previously disconnected, trigger refresh
        if (isConnected && !isConnectedBefore) {
            viewModel.getMangaDetails()
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
                .systemBarsPadding()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) { page ->
                val image = uiState.mangaPicturesList[page]

                if (uiState.mangaPicturesList.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = { offset ->
                                        // calculate the tap position relative to the image width
                                        val tapX = offset.x / size.width

                                        // if tapped on the right half, navigate to next page
                                        if (tapX > 0.5) {
                                            // navigate to next page
                                            scope.launch {
                                                pagerState.animateScrollToPage(page + 1)
                                            }
                                        } else {
                                            // navigate to previous page
                                            scope.launch {
                                                pagerState.animateScrollToPage(page - 1)
                                            }
                                        }
                                    }
                                )
                            }
                    ) {
                        // picture
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(image.large)
                                .crossfade(true)
                                .build(),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = stringResource(R.string.cd_media_picture),
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .scale(0.5f),
                                    color = MaterialTheme.colorScheme.surface,
                                    trackColor = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }


            /**
             * dots according to pictures count
             */
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) ColorDarkGray else ColorLightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(color)
                            .height(3.dp)
                            .weight(1f)
                    )
                }
            }


            /**
             * pictures top bar
             */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                    .statusBarsPadding()
            ) {
                // close button
                CustomButton(
                    icon = R.drawable.close,
                    iconSize = 25.dp,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.cd_close_icon),
                    isIconButton = true,
                    onClick = navActionManager::toBack
                )

                // open in browser button
                CustomButton(
                    icon = R.drawable.open_in_browser,
                    iconSize = 25.dp,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.cd_open_in_browser_icon),
                    isIconButton = true,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("$URL_MAL_MANGA${uiState.mangaDetail?.id}/${uiState.mangaDetail?.title}/pics")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )
            }


            /**
             * loading & error state
             */
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (!uiState.errorMessage.isNullOrEmpty()) {
                    MediaPicturesErrorView()
                }

                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 85.dp)
                            .size(150.dp),
                        color = MaterialTheme.colorScheme.surface,
                        trackColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

}