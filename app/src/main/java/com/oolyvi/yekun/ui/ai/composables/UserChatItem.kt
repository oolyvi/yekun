package com.oolyvi.yekun.ui.ai.composables

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.ai.AiUiState
import com.oolyvi.yekun.ui.theme.ColorTransparent

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UserChatItem(
    prompt: String,
    bitmap: Bitmap?,
    sheetState: SheetState,
    context: Context,
    clipboardManager: ClipboardManager,
    interactionSource: MutableInteractionSource,
    lottieComposition: LottieComposition,
    vibrator: Vibrator,
    uiState: AiUiState,
    showBottomSheet: (Boolean) -> Unit,
    isLastItem: Boolean
) {

    Spacer(modifier = Modifier.width(60.dp))

    BoxWithConstraints(
        modifier = Modifier
            .padding(start = 60.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            // show username of MAL, if it is null, it will show title You
            Text(
                text = stringResource(R.string.you),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 80.dp),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
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
                    .clip(RoundedCornerShape(25.dp)),
                colors = CardDefaults.cardColors(containerColor = ColorTransparent),
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    bitmap?.let {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .padding(bottom = 10.dp)
                                .clip(RoundedCornerShape(25.dp)),
                            contentDescription = stringResource(R.string.cd_sent_image),
                            contentScale = ContentScale.Crop,
                            bitmap = it.asImageBitmap()
                        )
                    }

                    // user prompt
                    Text(
                        text = prompt,
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .wrapContentWidth()
                            .background(MaterialTheme.colorScheme.onSurface)
                            .padding(20.dp),
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }

            // show loading indicator on just last message item
            if (isLastItem && uiState.isLoading) {
                LottieAnimation(
                    composition = lottieComposition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }


    /**
     * bottom sheet long click on user chat item
     */
    if (uiState.showUserBottomSheet == true) {
        ChatSheet(
            context = context,
            clipboardManager = clipboardManager,
            message = prompt,
            sheetState = sheetState,
            showBottomSheet = showBottomSheet,
            bottomPadding = WindowInsets.navigationBars.asPaddingValues()
                .calculateBottomPadding()
        )
    }

}