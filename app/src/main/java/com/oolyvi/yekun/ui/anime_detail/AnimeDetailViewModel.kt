package com.oolyvi.yekun.ui.anime_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.data.repository.AnimeRepository
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
class AnimeDetailViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimeDetailUiState())
    val uiState: StateFlow<AnimeDetailUiState> = _uiState.asStateFlow()

    private val animeId = savedStateHandle.get<Int>("id")


    init {
        if (animeId != null) {
            getAnimeDetails()
        }
    }


    /**
     * get anime details
     */
    fun getAnimeDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val response = animeId?.let {
                    animeRepository.getAnimeDetails(
                        animeId = it
                    )
                }
                val characterResponse = animeId?.let {
                    animeRepository.getAnimeChars(
                        id = it
                    )
                }
                _uiState.update {
                    it.copy(
                        animeDetail = response,
                        userAnimeListStatus = response?.myListStatus,
                        relatedAnimeList = response?.relatedAnime ?: emptyList(),
                        recommendedAnimeList = response?.recommendations ?: emptyList(),
                        animePicturesList = response?.pictures ?: emptyList(),
                        animeCharsList = characterResponse?.body()?.data ?: emptyList(),
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
     * update anime list
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUserAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {

            val animeId = _uiState.value.animeDetail?.id ?: return@launch

            val statusValue =
                if (_uiState.value.status != _uiState.value.userAnimeListStatus?.status)
                    _uiState.value.status else null
            val scoreValue = if (_uiState.value.score != _uiState.value.userAnimeListStatus?.score)
                _uiState.value.score else null
            val episodeValue =
                if (_uiState.value.numWatchedEpisodes != _uiState.value.userAnimeListStatus?.numEpisodesWatched)
                    _uiState.value.numWatchedEpisodes else null
            val isRewatchingValue =
                if (_uiState.value.isRewatching != _uiState.value.userAnimeListStatus?.isRewatching)
                    _uiState.value.isRewatching else null
            val repeatCountValue =
                if (_uiState.value.numTimesRewatched != _uiState.value.userAnimeListStatus?.numTimesRewatched)
                    _uiState.value.numTimesRewatched else null
            val repeatValueValue =
                if (_uiState.value.rewatchValue != _uiState.value.userAnimeListStatus?.rewatchValue)
                    _uiState.value.rewatchValue else null
            val priorityValue =
                if (_uiState.value.priority != _uiState.value.userAnimeListStatus?.priority)
                    _uiState.value.priority else null
            val tagsValue =
                if (_uiState.value.tags != _uiState.value.userAnimeListStatus?.tags?.joinToString())
                    _uiState.value.tags else null
            val commentsValue =
                if (_uiState.value.comments != _uiState.value.userAnimeListStatus?.comments)
                    _uiState.value.comments else null
            val startDateISO = _uiState.value.startDate?.format(DateTimeFormatter.ISO_DATE)
            val startDateValue = if (startDateISO != _uiState.value.userAnimeListStatus?.startDate)
                startDateISO else null
            val endDateISO = _uiState.value.finishDate?.format(DateTimeFormatter.ISO_DATE)
            val endDateValue = if (endDateISO != _uiState.value.userAnimeListStatus?.finishDate)
                endDateISO else null

            try {
                _uiState.update {
                    it.copy(
                        isUpdated = false,
                        errorMessage = null
                    )
                }
                val response = animeId.let {
                    animeRepository.updateUserAnimeList(
                        animeId = it,
                        status = statusValue,
                        score = scoreValue,
                        numEpisodesWatched = episodeValue,
                        isRewatching = isRewatchingValue,
                        startDate = startDateValue,
                        finishDate = endDateValue,
                        priority = priorityValue,
                        numTimesRewatched = repeatCountValue,
                        rewatchValue = repeatValueValue,
                        tags = tagsValue,
                        comments = commentsValue
                    )
                }
                _uiState.update {
                    it.copy(
                        userAnimeListStatus = response,
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
     * delete anime entry
     */
    fun deleteAnimeEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            val animeId = _uiState.value.animeDetail?.id ?: return@launch

            try {
                _uiState.update {
                    it.copy(
                        isDeleted = false,
                        errorMessage = null
                    )
                }
                val result = animeRepository.deleteAnimeEntry(
                    animeId = animeId
                )
                _uiState.update {
                    it.copy(
                        userAnimeListStatus = if (result) null else it.userAnimeListStatus,
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
    fun setEditVariables(listStatus: AnimeDetail.MyListStatus) {
        _uiState.update {
            it.copy(
                status = listStatus.status,
                score = listStatus.score ?: 0,
                startDate = getLocalDateFromDateString(listStatus.startDate),
                finishDate = getLocalDateFromDateString(listStatus.finishDate),
                numWatchedEpisodes = listStatus.numEpisodesWatched ?: 0,
                isRewatching = listStatus.isRewatching ?: false,
                numTimesRewatched = listStatus.numTimesRewatched ?: 0,
                rewatchValue = listStatus.rewatchValue ?: 0,
                priority = listStatus.priority ?: 0,
                tags = listStatus.tags?.joinToString(),
                comments = listStatus.comments
            )
        }
    }


    /**
     * change animation played for watching stats
     */
    fun onChangeAnimationPlayed(value: Boolean) {
        _uiState.update {
            it.copy(isStatsAnimationPlayed = value)
        }
    }


    /**
     * set items for updating list
     */
    fun setMediaInfo(value: AnimeDetail) {
        _uiState.update { it.copy(animeDetail = value) }
    }

    fun onChangeStatus(value: String?) {
        _uiState.update {
            it.copy(
                status = value
            )
        }
    }

    fun onChangeEpisodeCount(value: Int?) {
        _uiState.update {
            it.copy(
                numWatchedEpisodes = if (value != null && value >= 0 || value == null) value
                else it.numWatchedEpisodes
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

    fun onChangeIsRewatching(value: Boolean) {
        _uiState.update { it.copy(isRewatching = value) }
    }

    fun onChangeRepeatCount(value: Int?) {
        _uiState.update {
            it.copy(
                numTimesRewatched = if (value != null && value >= 0 || value == null) value
                else it.numTimesRewatched
            )
        }
    }

    fun onChangeRewatchValue(value: Int) {
        _uiState.update { it.copy(rewatchValue = value) }
    }

    fun onChangeComments(value: String) {
        _uiState.update { it.copy(comments = value) }
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
     * show synopsis
     */
    fun showSynopsis(value: Boolean) {
        _uiState.update {
            it.copy(showSynopsis = value)
        }
    }

    /**
     * show themes
     */
    fun showThemes(value: Boolean) {
        _uiState.update {
            it.copy(showThemes = value)
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