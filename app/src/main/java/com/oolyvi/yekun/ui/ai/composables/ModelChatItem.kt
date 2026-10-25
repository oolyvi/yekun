package com.oolyvi.yekun.ui.ai.composables

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.ai.AiUiState
import com.oolyvi.yekun.ui.theme.ColorPrimary
import com.oolyvi.yekun.ui.theme.ColorWhite

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ModelChatItem(
    response: String,
    context: Context,
    sheetState: SheetState,
    clipboardManager: ClipboardManager,
    interactionSource: MutableInteractionSource,
    vibrator: Vibrator,
    uiState: AiUiState,
    showBottomSheet: (Boolean) -> Unit
) {

    BoxWithConstraints(
        modifier = Modifier
            .padding(end = 60.dp)
    ) {
        Column {
            // gemini ai title on top of the box
            Text(
                text = stringResource(R.string.ai),
                modifier = Modifier
                    .wrapContentWidth(),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Card(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                            showBottomSheet(true)
                        },
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(ColorPrimary)
                ) {
                    // model response
                    Text(
                        text = response,
                        color = ColorWhite,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(20.dp),
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.width(60.dp))


    /**
     * bottom sheet long click on chat item
     */
    if (uiState.showModelBottomSheet == true) {
        ChatSheet(
            context = context,
            clipboardManager = clipboardManager,
            message = response,
            sheetState = sheetState,
            showBottomSheet = showBottomSheet,
            bottomPadding = WindowInsets.navigationBars.asPaddingValues()
                .calculateBottomPadding()
        )
    }

}