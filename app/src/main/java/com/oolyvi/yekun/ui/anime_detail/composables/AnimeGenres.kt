package com.oolyvi.yekun.ui.anime_detail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.composables.lazy_items.GenreChipItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeGenres(
    animeDetail: AnimeDetail
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        if (animeDetail.genres?.map { it.name } != null) {
            FlowRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 5
            )
            {
                animeDetail.genres.map { it.name }.forEach {
                    if (it != null) {
                        GenreChipItem(it)
                    }
                }
            }
        }
    }

}
