package com.oolyvi.yekun.ui.ai

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Vibrator
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.ai.ChatData.getAiBitmap
import com.oolyvi.yekun.ui.ai.composables.ChatContent
import com.oolyvi.yekun.ui.composables.fab_buttons.AiFab
import com.oolyvi.yekun.ui.composables.top_bars.MainCenterAlignedTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.utils.isScrollingUpList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AiScreen(
    navActionManager: NavActionManager
) {

    val viewModel: AiViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val vibrator = context.getSystemService(Vibrator::class.java)
    val uiState by viewModel.uiState.collectAsState()
    val bitmap = getAiBitmap()
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.ai_loading_error_view))
    val sendClickSound: MediaPlayer = MediaPlayer.create(context, R.raw.send_click)
    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            ChatData.uriState.update { uri.toString() }
        }
    }

    // initial value of send click
    val initialIsSendSoundPlayedValue = runBlocking {
        viewModel.isSendSoundPlayed.first()
    }



    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding(),
            topBar = {
                MainCenterAlignedTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    topAppBarType = TopAppBarType.AI,
                    selectedStatus = null,
                    isLoading = null,
                    sortedUserListSize = null,
                    isLoggedIn = null,
                    onSortClick = { }
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = !listState.isScrollingUpList(),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    AiFab(listState = listState, scope = scope)
                }
            }
        ) {
            lottieComposition?.let { it1 ->
                ChatContent(
                    uiState = uiState,
                    bitmap = bitmap,
                    context = context,
                    clipboardManager = clipboardManager,
                    sheetState = sheetState,
                    interactionSource = interactionSource,
                    vibrator = vibrator,
                    lottieComposition = it1,
                    addClick = {
                        imagePicker.launch(
                            PickVisualMediaRequest
                                .Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                .build()
                        )
                    },
                    updatePrompt = {
                        viewModel.updatePrompt(it)
                    },
                    sendPrompt = {
                        viewModel.sendPrompt(uiState.prompt, bitmap)

                        if (initialIsSendSoundPlayedValue) {
                            sendClickSound.start()
                        }

                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    listState = listState,
                    showUserBottomSheet = {
                        viewModel.showUserBottomSheet(it)
                    },
                    showModelBottomSheet = {
                        viewModel.showModelBottomSheet(it)
                    }
                )
            }
        }
    }

}