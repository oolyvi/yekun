package com.oolyvi.yekun.ui.ai.composables

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.oolyvi.yekun.ui.ai.AiUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ChatContent(
    uiState: AiUiState,
    bitmap: Bitmap?,
    context: Context,
    interactionSource: MutableInteractionSource,
    lottieComposition: LottieComposition,
    vibrator: Vibrator,
    sheetState: SheetState,
    clipboardManager: ClipboardManager,
    listState: LazyListState,
    sendPrompt: () -> Unit,
    addClick: () -> Unit,
    showUserBottomSheet: (Boolean) -> Unit,
    showModelBottomSheet: (Boolean) -> Unit,
    updatePrompt: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                top = 80.dp,
                start = 20.dp,
                end = 20.dp
            ),
            reverseLayout = true,
            state = listState
        ) {
            itemsIndexed(uiState.chatUiStateList) { index, chat ->
                val isLastItem = index == 0

                // if chat is from user
                if (chat.isFromUser) {
                    UserChatItem(
                        prompt = chat.prompt,
                        bitmap = chat.bitmap,
                        context = context,
                        sheetState = sheetState,
                        clipboardManager = clipboardManager,
                        interactionSource = interactionSource,
                        vibrator = vibrator,
                        uiState = uiState,
                        showBottomSheet = showUserBottomSheet,
                        lottieComposition = lottieComposition,
                        isLastItem = isLastItem
                    )
                } else {
                    // if chat is from model
                    ModelChatItem(
                        response = chat.prompt,
                        context = context,
                        sheetState = sheetState,
                        clipboardManager = clipboardManager,
                        interactionSource = interactionSource,
                        vibrator = vibrator,
                        uiState = uiState,
                        showBottomSheet = showModelBottomSheet
                    )
                }
            }
        }


        /**
         * text field row
         */
        AiUserInput(
            bitmap = bitmap,
            uiState = uiState,
            updatePrompt = updatePrompt,
            addClick = addClick,
            sendPrompt = sendPrompt
        )
    }

}