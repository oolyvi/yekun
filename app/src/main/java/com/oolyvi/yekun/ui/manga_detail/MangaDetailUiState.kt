package com.oolyvi.yekun.ui.manga_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.utils.formatDateStartEnd
import com.oolyvi.yekun.utils.toLocalized
import java.text.NumberFormat
import java.time.LocalDate

@Immutable
data class MangaDetailUiState(
    val mangaDetail: MangaDetail? = null,
    val relatedMangaList: List<MangaDetail.RelatedManga> = emptyList(),
    val recommendedMangaList: List<MangaDetail.Recommendation> = emptyList(),
    val mangaPicturesList: List<MangaDetail.Picture> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showBottomSheet: Boolean = false,
    val showSynopsis: Boolean = false,
    val isUpdated: Boolean? = false,
    val isDeleted: Boolean? = false,
    val userMangaListStatus: MangaDetail.MyListStatus? = null,
    val status: String? = "plan_to_read",
    val numChaptersRead: Int? = null,
    val numVolumesRead: Int? = null,
    val score: Int = 0,
    val startDate: LocalDate? = null,
    val finishDate: LocalDate? = null,
    val openStartDatePicker: Boolean = false,
    val openFinishDatePicker: Boolean = false,
    val tags: String? = null,
    val priority: Int = 0,
    val isRereading: Boolean = false,
    val numTimesRereads: Int? = null,
    val rereadValue: Int = 0,
    val comments: String? = null,
    val showDeleteDialog: Boolean = false
) {

    // if is new entry, there is not any delete button
    val isNewEntry
        get() = userMangaListStatus == null

    val formattedStartDate = mangaDetail?.startDate?.let {
        formatDateStartEnd(it)
    }
    val formattedRank =
        mangaDetail?.rank?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"
    val formattedNumScoringUser =
        mangaDetail?.numScoringUsers?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"
    val formattedNumListUser =
        mangaDetail?.numListUsers?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"
    val formattedPopularity =
        mangaDetail?.popularity?.let { NumberFormat.getNumberInstance().format(it) } ?: "-"


    @RequiresApi(Build.VERSION_CODES.O)
    val startDateTitle = startDate.toLocalized()

    @RequiresApi(Build.VERSION_CODES.O)
    val endDateTitle = finishDate.toLocalized()

}