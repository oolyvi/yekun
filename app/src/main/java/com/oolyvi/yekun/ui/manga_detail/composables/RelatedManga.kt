package com.oolyvi.yekun.ui.manga_detail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.lazy_items.RelatedItem
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.manga_detail.MangaDetailUiState
import com.oolyvi.yekun.utils.GeneralMediaType

@Composable
fun RelatedManga(
    uiState: MangaDetailUiState,
    navActionManager: NavActionManager
) {

    if (uiState.relatedMangaList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.related),
                fontSize = 17.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
    }

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(uiState.relatedMangaList) { relatedManga ->
            RelatedItem(
                relatedMedia = relatedManga,
                mediaType = GeneralMediaType.MANGA,
                onItemClick = {
                    relatedManga.node?.id?.let { it1 -> navActionManager.toMangaDetails(it1) }
                }
            )
        }
    }

}