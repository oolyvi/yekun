package com.oolyvi.yekun.ui.anime_detail.composables

import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.anime_detail.AnimeDetailUiState
import com.oolyvi.yekun.ui.main.navigation.NavActionManager

@Composable
fun AnimeDetailItem(
    animeDetail: AnimeDetail,
    context: Context,
    uiState: AnimeDetailUiState,
    interactionSource: MutableInteractionSource,
    clipboardManager: ClipboardManager,
    navActionManager: NavActionManager,
    showSynopsis: (Boolean) -> Unit,
    showThemes: (Boolean) -> Unit,
    changeAnimationPlayed: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // poster image
        AnimePicturePoster(
            animeDetail = animeDetail,
            navActionManager = navActionManager
        )

        Spacer(modifier = Modifier.height(15.dp))

        // anime name, score... part
        AnimeName(
            context = context,
            clipboardManager = clipboardManager,
            animeDetail = animeDetail
        )

        // genres
        AnimeGenres(animeDetail = animeDetail)

        // synopsis
        AnimeSynopsis(
            animeDetail = animeDetail,
            uiState = uiState,
            showSynopsis = showSynopsis,
            interactionSource = interactionSource
        )

        // anime info
        AnimeInfos(
            animeDetail = animeDetail,
            context = context,
            uiState = uiState,
            clipboardManager = clipboardManager
        )

        Spacer(modifier = Modifier.height(20.dp))

        // anime watching stats
        animeDetail.statistics?.let {
            AnimeWatchingStats(
                animeWatchingStats = it,
                uiState = uiState,
                changeAnimationPlayed = changeAnimationPlayed
            )
        }

        // anime openings and endings
        AnimeThemes(
            animeDetail = animeDetail,
            context = context,
            uiState = uiState,
            showThemes = showThemes
        )

        Spacer(modifier = Modifier.height(20.dp))

        // anime characters
        AnimeChars(uiState)

        // related anime
        RelatedAnime(
            uiState = uiState,
            navActionManager = navActionManager
        )

        // recommended anime
        RecommendedAnime(
            uiState = uiState,
            navActionManager = navActionManager
        )
    }

}