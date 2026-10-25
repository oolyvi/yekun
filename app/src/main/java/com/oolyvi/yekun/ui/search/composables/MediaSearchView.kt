package com.oolyvi.yekun.ui.search.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.ui.composables.lazy_items.MediaSearchItem
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.search.SearchUiState
import com.oolyvi.yekun.utils.GeneralMediaType

@Composable
fun MediaSearchView(
    listState: LazyListState,
    uiState: SearchUiState,
    mediaType: GeneralMediaType,
    navActionManager: NavActionManager
) {

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 50.dp),
    ) {
        when (mediaType) {
            /**
             * if search is for Anime
             */
            GeneralMediaType.ANIME -> {
                items(
                    uiState.animeSearchList,
                    key = { it.node?.id ?: 0 }
                ) { searchedAnime ->
                    MediaSearchItem(
                        searchedMedia = searchedAnime,
                        mediaType = mediaType,
                        onItemClick = {
                            searchedAnime.node?.id?.let { navActionManager.toAnimeDetails(it) }
                        }
                    )
                }
            }

            /**
             * if search is for Manga
             */
            GeneralMediaType.MANGA -> {
                items(
                    uiState.mangaSearchList,
                    key = { it.node?.id ?: 0 }
                ) { searchedManga ->
                    MediaSearchItem(
                        searchedMedia = searchedManga,
                        mediaType = mediaType,
                        onItemClick = {
                            searchedManga.node?.id?.let { navActionManager.toMangaDetails(it) }
                        }
                    )
                }
            }
        }
    }

}