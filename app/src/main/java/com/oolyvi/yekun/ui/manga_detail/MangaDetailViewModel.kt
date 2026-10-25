package com.oolyvi.yekun.ui.manga_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.data.repository.MangaRepository
import com.oolyvi.yekun.utils.getLocalDateFromDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val mangaRepository: MangaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MangaDetailUiState())
    val uiState: StateFlow<MangaDetailUiState> = _uiState.asStateFlow()

    val mangaId = savedStateHandle.get<Int>("id")


    init {
        if (mangaId != null) {
            getMangaDetails()
        }
    }


    /**
     * get manga details
     */
    fun getMangaDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val response = mangaId?.let {
                    mangaRepository.getMangaDetails(mangaId = it)
                }
                _uiState.update {
                    it.copy(
                        mangaDetail = response,
                        userMangaListStatus = response?.myListStatus,
                        relatedMangaList = response?.relatedManga ?: emptyList(),
                        recommendedMangaList = response?.recommendations ?: emptyList(),
                        mangaPicturesList = response?.pictures ?: emptyList(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage?.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }


    /**
     * update manga list
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUserMangaList() {
        viewModelScope.launch(Dispatchers.IO) {

            val mangaId = _uiState.value.mangaDetail?.id ?: return@launch

            val statusValue =
                if (_uiState.value.status != _uiState.value.userMangaListStatus?.status)
                    _uiState.value.status else null
            val scoreValue = if (_uiState.value.score != _uiState.value.userMangaListStatus?.score)
                _uiState.value.score else null
            val numChaptersValue =
                if (_uiState.value.numChaptersRead != _uiState.value.userMangaListStatus?.numChaptersRead)
                    _uiState.value.numChaptersRead else null
            val numVolumesValue =
                if (_uiState.value.numVolumesRead != _uiState.value.userMangaListStatus?.numVolumesRead)
                    _uiState.value.numVolumesRead else null
            val isRereadingValue =
                if (_uiState.value.isRereading != _uiState.value.userMangaListStatus?.isRereading)
                    _uiState.value.isRereading else null
            val numTimesRereadValue =
                if (_uiState.value.numTimesRereads != _uiState.value.userMangaListStatus?.numTimesReread)
                    _uiState.value.numTimesRereads else null
            val rereadValue =
                if (_uiState.value.rereadValue != _uiState.value.userMangaListStatus?.rereadValue)
                    _uiState.value.rereadValue else null
            val priorityValue =
                if (_uiState.value.priority != _uiState.value.userMangaListStatus?.priority)
                    _uiState.value.priority else null
            val tagsValue =
                if (_uiState.value.tags != _uiState.value.userMangaListStatus?.tags?.joinToString())
                    _uiState.value.tags else null
            val commentsValue =
                if (_uiState.value.comments != _uiState.value.userMangaListStatus?.comments)
                    _uiState.value.comments else null
            val startDateISO = _uiState.value.startDate?.format(DateTimeFormatter.ISO_DATE)
            val startDateValue = if (startDateISO != _uiState.value.userMangaListStatus?.startDate)
                startDateISO else null
            val endDateISO = _uiState.value.finishDate?.format(DateTimeFormatter.ISO_DATE)
            val endDateValue = if (endDateISO != _uiState.value.userMangaListStatus?.finishDate)
                endDateISO else null

            try {
                _uiState.update {
                    it.copy(
                        isUpdated = false,
                        errorMessage = null
                    )
                }
                val response = mangaId.let {
                    mangaRepository.updateUserMangaList(
                        mangaId = it,
                        status = statusValue,
                        score = scoreValue,
                        numChaptersRead = numChaptersValue,
                        numVolumesRead = numVolumesValue,
                        isRereading = isRereadingValue,
                        startDate = startDateValue,
                        finishDate = endDateValue,
                        priority = priorityValue,
                        numTimesReread = numTimesRereadValue,
                        rereadValue = rereadValue,
                        tags = tagsValue,
                        comments = commentsValue
                    )
                }
                _uiState.update {
                    it.copy(
                        userMangaListStatus = response,
                        isUpdated = true,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage?.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }


    /**
     * delete manga entry
     */
    fun deleteMangaEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            val mangaId = _uiState.value.mangaDetail?.id ?: return@launch

            try {
                _uiState.update {
                    it.copy(
                        isDeleted = false,
                        errorMessage = null
                    )
                }
                val result = mangaRepository.deleteMangaEntry(
                    mangaId = mangaId
                )
                _uiState.update {
                    it.copy(
                        userMangaListStatus = if (result) null else it.userMangaListStatus,
                        errorMessage = null,
                        isDeleted = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage?.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * set variable uiState according to data class
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setEditVariables(listStatus: MangaDetail.MyListStatus) {
        _uiState.update {
            it.copy(
                userMangaListStatus = listStatus,
                status = listStatus.status,
                score = listStatus.score ?: 0,
                startDate = getLocalDateFromDateString(listStatus.startDate),
                finishDate = getLocalDateFromDateString(listStatus.finishDate),
                numChaptersRead = listStatus.numChaptersRead ?: 0,
                numVolumesRead = listStatus.numVolumesRead ?: 0,
                isRereading = listStatus.isRereading ?: false,
                numTimesRereads = listStatus.numTimesReread ?: 0,
                rereadValue = listStatus.rereadValue ?: 0,
                priority = listStatus.priority ?: 0,
                tags = listStatus.tags?.joinToString(),
                comments = listStatus.comments
            )
        }
    }


    /**
     * set items for updating list
     */
    fun setMediaInfo(value: MangaDetail) {
        _uiState.update { it.copy(mangaDetail = value) }
    }

    fun onChangeStatus(value: String?) {
        _uiState.update {
            it.copy(
                status = value
            )
        }
    }

    fun onChangeChaptersCount(value: Int?) {
        _uiState.update {
            it.copy(
                numChaptersRead = if (value != null && value >= 0 || value == null) value
                else it.numChaptersRead
            )
        }
    }

    fun onChangeVolumesCount(value: Int?) {
        _uiState.update {
            it.copy(
                numVolumesRead = if (value != null && value >= 0 || value == null) value
                else it.numVolumesRead
            )
        }
    }

    fun onChangeScore(value: Int) {
        _uiState.update { it.copy(score = value) }
    }

    fun openStartDatePicker() {
        _uiState.update {
            it.copy(openStartDatePicker = true)
        }
    }

    fun openEndDatePicker() {
        _uiState.update {
            it.copy(openFinishDatePicker = true)
        }
    }

    fun onChangeStartDate(value: LocalDate?) {
        _uiState.update {
            it.copy(
                startDate = value,
                openStartDatePicker = false
            )
        }
    }

    fun onChangeEndDate(value: LocalDate?) {
        _uiState.update {
            it.copy(
                finishDate = value,
                openFinishDatePicker = false
            )
        }
    }

    fun closeDatePickers() {
        _uiState.update {
            it.copy(
                openStartDatePicker = false,
                openFinishDatePicker = false
            )
        }
    }

    fun onChangeTags(value: String) {
        _uiState.update { it.copy(tags = value) }
    }

    fun onChangePriority(value: Int) {
        _uiState.update { it.copy(priority = value) }
    }

    fun onChangeIsRereading(value: Boolean) {
        _uiState.update { it.copy(isRereading = value) }
    }

    fun onChangeRepeatCount(value: Int?) {
        _uiState.update {
            it.copy(
                numTimesRereads = if (value != null && value >= 0 || value == null) value
                else it.numTimesRereads
            )
        }
    }

    fun onChangeRereadValue(value: Int) {
        _uiState.update { it.copy(rereadValue = value) }
    }

    fun onChangeComments(value: String) {
        _uiState.update { it.copy(comments = value) }
    }


    /**
     * show synopsis
     */
    fun showSynopsis(value: Boolean) {
        _uiState.update {
            it.copy(showSynopsis = value)
        }
    }

    /**
     * show bottom sheet
     */
    fun showBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(showBottomSheet = value)
        }
    }

    /**
     * show delete dialog
     */
    fun showDeleteDialog(value: Boolean) {
        _uiState.update {
            it.copy(showDeleteDialog = value)
        }
    }

}