package com.oolyvi.yekun.ui.anime_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.anime.AnimeChar
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.theme.ColorAnimeDetailsStats1
import com.oolyvi.yekun.ui.theme.ColorAnimeDetailsStats2
import com.oolyvi.yekun.ui.theme.ColorAnimeDetailsStats3
import com.oolyvi.yekun.ui.theme.ColorAnimeDetailsStats4
import com.oolyvi.yekun.ui.theme.ColorAnimeDetailsStats5
import com.oolyvi.yekun.utils.formatDateStartEnd
import com.oolyvi.yekun.utils.toLocalized
import java.text.NumberFormat
import java.time.LocalDate

@Immutable
data class AnimeDetailUiState(
    val animeDetail: AnimeDetail? = null,
    val relatedAnimeList: List<AnimeDetail.RelatedAnime> = emptyList(),
    val recommendedAnimeList: List<AnimeDetail.Recommendation> = emptyList(),
    val animePicturesList: List<AnimeDetail.Picture> = emptyList(),
    val animeCharsList: List<AnimeChar.Data> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userAnimeListStatus: AnimeDetail.MyListStatus? = null,
    val isUpdated: Boolean? = false,
    val isDeleted: Boolean? = false,
    val isStatsAnimationPlayed: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showSynopsis: Boolean = false,
    val showThemes: Boolean = false,
    val status: String? = "plan_to_watch",
    val numWatchedEpisodes: Int? = null,
    val score: Int = 0,
    val startDate: LocalDate? = null,
    val finishDate: LocalDate? = null,
    val openStartDatePicker: Boolean = false,
    val openFinishDatePicker: Boolean = false,
    val tags: String? = null,
    val priority: Int = 0,
    val isRewatching: Boolean = false,
    val numTimesRewatched: Int? = null,
    val rewatchValue: Int = 0,
    val comments: String? = null,
    val showDeleteDialog: Boolean = false
) {

    // if is new entry, there is not any delete button
    val isNewEntry
        get() = userAnimeListStatus == null

    val formattedStartDate = animeDetail?.startDate?.let {
        formatDateStartEnd(it)
    }
    val formattedEndDate = animeDetail?.endDate?.let {
        formatDateStartEnd(it)
    }

    val formattedRank =
        animeDetail?.rank?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"
    val formattedNumScoringUser =
        animeDetail?.numScoringUsers?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"
    val formattedNumListUser =
        animeDetail?.numListUsers?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"
    val formattedPopularity =
        animeDetail?.popularity?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"


    private val seconds = animeDetail?.averageEpisodeDuration
    private val minutes = seconds?.div(60)
    val hours = minutes?.div(60)
    val remainingMinutes = minutes?.rem(60)
    val durationWithMinute = animeDetail?.averageEpisodeDuration?.div(60)


    val watchingStatsColors = listOf(
        ColorAnimeDetailsStats1,
        ColorAnimeDetailsStats2,
        ColorAnimeDetailsStats3,
        ColorAnimeDetailsStats4,
        ColorAnimeDetailsStats5
    )


    @RequiresApi(Build.VERSION_CODES.O)
    val startDateTitle = startDate.toLocalized()

    @RequiresApi(Build.VERSION_CODES.O)
    val endDateTitle = finishDate.toLocalized()

}