package com.oolyvi.yekun.data.repository

import com.oolyvi.yekun.data.model.anime.AnimeChar
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.data.model.anime.AnimeForYou
import com.oolyvi.yekun.data.model.anime.AnimeList
import com.oolyvi.yekun.data.model.anime.AnimeRanking
import com.oolyvi.yekun.data.model.anime.AnimeSeason
import com.oolyvi.yekun.data.model.anime.AnimeUserList
import com.oolyvi.yekun.data.network.JikanApi
import com.oolyvi.yekun.data.network.MalApi
import com.oolyvi.yekun.data.network.TokenApi
import com.oolyvi.yekun.utils.ApiConstants.ANIME_DETAIL_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.COMMON_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.RANKED_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.SEARCH_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.SEARCH_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.SEASON_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.SEASON_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.USER_ANIME_LIST_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.USER_LIST_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.OFFSET_PAGE
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val malApi: MalApi,
    private val jikanApi: JikanApi,
    tokenApi: TokenApi,
    defaultPreferencesRepository: DefaultPreferencesRepository
) : BaseRepository(tokenApi, defaultPreferencesRepository) {

    suspend fun getSeasonAnime(
        year: Int,
        season: String,
        sort: String
    ): Response<AnimeSeason> {
        return malApi.getSeasonAnime(
            year = year,
            season = season,
            limit = SEASON_PAGE_SIZE,
            offset = OFFSET_PAGE,
            sort = sort,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            fields = SEASON_FIELDS
        )
    }

    suspend fun getAnimeDetails(
        animeId: Int
    ): AnimeDetail {
        val result = malApi.getAnimeDetails(
            animeId = animeId,
            fields = ANIME_DETAIL_FIELDS
        )
        result.error?.let { handleResponseError(it) }
        return result
    }

    suspend fun getAnimeRanked(rankingType: String): Response<AnimeRanking> {
        return malApi.getAnimeRanked(
            rankingType = rankingType,
            limit = COMMON_PAGE_SIZE,
            offset = OFFSET_PAGE,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            fields = RANKED_FIELDS
        )
    }

    suspend fun searchAnime(query: String): Response<AnimeList> {
        return malApi.searchAnime(
            query = query,
            limit = SEARCH_PAGE_SIZE,
            offset = OFFSET_PAGE,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            fields = SEARCH_FIELDS
        )
    }

    suspend fun getAnimeForYou(): Response<AnimeForYou> {
        val result = malApi.getAnimeForYou(
            limit = COMMON_PAGE_SIZE,
            offset = OFFSET_PAGE,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            fields = SEARCH_FIELDS
        )
        result.body()?.error?.let { handleResponseError(it) }
        return result
    }

    suspend fun getAnimeChars(id: Int): Response<AnimeChar> {
        return jikanApi.getAnimeChars(
            id = id
        )
    }

    suspend fun getUserAnimeList(
        status: String,
        sort: String,
    ): Response<AnimeUserList> {
        val result = malApi.getUserAnimeList(
            limit = USER_LIST_PAGE_SIZE,
            offset = OFFSET_PAGE,
            fields = USER_ANIME_LIST_FIELDS,
            status = status,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            sort = sort
        )
        result.body()?.error?.let { handleResponseError(it) }
        return result
    }

    suspend fun updateUserAnimeList(
        animeId: Int,
        status: String? = null,
        score: Int? = null,
        numEpisodesWatched: Int? = null,
        isRewatching: Boolean? = null,
        startDate: String? = null,
        finishDate: String? = null,
        priority: Int? = null,
        numTimesRewatched: Int? = null,
        rewatchValue: Int? = null,
        tags: String? = null,
        comments: String? = null
    ): AnimeDetail.MyListStatus? {
        return try {
            val result = malApi.updateUserAnimeList(
                animeId = animeId,
                status = status,
                score = score,
                numWatchedEpisodes = numEpisodesWatched,
                isRewatching = isRewatching,
                startDate = startDate,
                finishDate = finishDate,
                priority = priority,
                numTimesRewatched = numTimesRewatched,
                rewatchValue = rewatchValue,
                tags = tags,
                comments = comments
            )
            result.error?.let { handleResponseError(it) }
            return result
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteAnimeEntry(
        animeId: Int
    ): Boolean {
        return try {
            val result = malApi.deleteAnimeEntry(animeId)
            if (result.isSuccessful) {
                return result.code() == HttpURLConnection.HTTP_OK
            } else {
                return result.code() == HttpURLConnection.HTTP_NOT_FOUND
            }
        } catch (e: Exception) {
            false
        }
    }

}