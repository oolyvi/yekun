package com.oolyvi.yekun.ui.anime_detail.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.anime_detail.AnimeDetailUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeSynopsis(
    animeDetail: AnimeDetail,
    uiState: AnimeDetailUiState,
    interactionSource: MutableInteractionSource,
    showSynopsis: (Boolean) -> Unit
) {

    if (!animeDetail.synopsis.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.synopsis),
                fontSize = 17.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 15.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // synopsis
            Text(
                text = animeDetail.synopsis,
                fontSize = 15.sp,
                maxLines = if (uiState.showSynopsis) Int.MAX_VALUE else 5,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    .combinedClickable(
                        onClick = {
                            if (uiState.showSynopsis) showSynopsis(false) else showSynopsis(true)
                        },
                        interactionSource = interactionSource,
                        indication = null
                    )
            )
        }
    }

}