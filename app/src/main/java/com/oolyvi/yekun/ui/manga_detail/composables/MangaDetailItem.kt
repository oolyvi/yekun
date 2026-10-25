package com.oolyvi.yekun.ui.manga_detail.composables

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
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.manga_detail.MangaDetailUiState

@Composable
fun MangaDetailItem(
    mangaDetail: MangaDetail,
    clipboardManager: ClipboardManager,
    uiState: MangaDetailUiState,
    interactionSource: MutableInteractionSource,
    context: Context,
    navActionManager: NavActionManager,
    showSynopsis: (Boolean) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // poster image
        MangaPicturePoster(
            mangaDetail = mangaDetail,
            navActionManager = navActionManager
        )

        Spacer(modifier = Modifier.height(15.dp))

        // name, score... part
        MangaName(
            context = context,
            clipboardManager = clipboardManager,
            mangaDetail = mangaDetail
        )

        // genres
        MangaGenres(mangaDetail = mangaDetail)

        // synopsis
        MangaSynopsis(
            mangaDetail = mangaDetail,
            uiState = uiState,
            interactionSource = interactionSource,
            showSynopsis = showSynopsis
        )

        // info
        MangaInfos(
            mangaDetail = mangaDetail,
            clipboardManager = clipboardManager,
            uiState = uiState,
            context = context
        )

        Spacer(modifier = Modifier.height(20.dp))

        // related
        RelatedManga(
            uiState = uiState,
            navActionManager = navActionManager
        )

        // recommended
        RecommendedManga(
            uiState = uiState,
            navActionManager = navActionManager
        )
    }

}