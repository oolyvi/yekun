package com.oolyvi.yekun.ui.composables.bottom_bars

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.utils.GeneralMediaType

/**
 * differentiate that it is Anime or Manga ui state
 */
sealed class MediaRankingUiState {
    data class AnimeRankingUiState(
        val itemList: List<String>,
        val selectedRankingType: String
    ) : MediaRankingUiState()

    data class MangaRankingUiState(
        val itemList: List<String>,
        val selectedRankingType: String
    ) : MediaRankingUiState()
}


/**
 * ranking bottom bar for both anime and manga ranking
 */
@Composable
fun RankingBottomBar(
    uiState: MediaRankingUiState,
    mediaType: GeneralMediaType,
    scrollToTop: () -> Unit,
    getFilteredRankedMedia: () -> Unit,
    onChangeRankingType: (String?) -> Unit
) {

    /**
     * item labels on bottom bar
     */
    val itemLabel: Map<String, String> = when (mediaType) {
        GeneralMediaType.ANIME -> mapOf(
            "all" to stringResource(R.string.all),
            "airing" to stringResource(R.string.airings),
            "upcoming" to stringResource(R.string.upcomings),
            "tv" to stringResource(R.string.tvs),
            "ova" to stringResource(R.string.ovas),
            "movie" to stringResource(R.string.movies),
            "special" to stringResource(R.string.specials),
            "bypopularity" to stringResource(R.string.populars),
            "favorite" to stringResource(R.string.favorites)
        )

        GeneralMediaType.MANGA -> mapOf(
            "all" to stringResource(id = R.string.all),
            "manga" to stringResource(R.string.mangas),
            "novels" to stringResource(R.string.novels),
            "oneshots" to stringResource(R.string.one_shots),
            "doujin" to stringResource(R.string.doujins),
            "manhwa" to stringResource(R.string.manhwas),
            "manhua" to stringResource(R.string.manhuas),
            "bypopularity" to stringResource(id = R.string.populars),
            "favorite" to stringResource(id = R.string.favorites)
        )
    }

    /**
     * item list for sorting
     */
    val itemList = when (uiState) {
        is MediaRankingUiState.AnimeRankingUiState -> uiState.itemList
        is MediaRankingUiState.MangaRankingUiState -> uiState.itemList
    }

    /**
     * selected ranking type
     */
    val selectedRankingType = when (uiState) {
        is MediaRankingUiState.AnimeRankingUiState -> uiState.selectedRankingType
        is MediaRankingUiState.MangaRankingUiState -> uiState.selectedRankingType
    }



    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(20.dp)
    ) {
        items(itemList) { item ->
            val isSelected = item == selectedRankingType
            val label = itemLabel[item] ?: stringResource(R.string.all)

            FilterChip(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .height(30.dp),
                selected = isSelected,
                colors = SelectableChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    disabledSelectedContainerColor = MaterialTheme.colorScheme.surface,

                    leadingIconColor = MaterialTheme.colorScheme.errorContainer,
                    trailingIconColor = MaterialTheme.colorScheme.errorContainer,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.errorContainer,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.errorContainer,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.errorContainer,
                    selectedTrailingIconColor = MaterialTheme.colorScheme.errorContainer
                ),
                shape = CircleShape,
                onClick = {
                    if (!isSelected) {
                        onChangeRankingType(item)
                        getFilteredRankedMedia()
                        scrollToTop()
                    }
                },
                label = {
                    Text(text = label)
                }
            )
        }
    }

}