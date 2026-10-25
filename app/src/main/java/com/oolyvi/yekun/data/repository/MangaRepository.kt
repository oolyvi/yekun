package com.oolyvi.yekun.data.repository

import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.data.model.manga.MangaList
import com.oolyvi.yekun.data.model.manga.MangaRanking
import com.oolyvi.yekun.data.model.manga.MangaUserList
import com.oolyvi.yekun.data.network.MalApi
import com.oolyvi.yekun.data.network.TokenApi
import com.oolyvi.yekun.utils.ApiConstants.COMMON_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.MANGA_DETAIL_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.SEARCH_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.SEARCH_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.USER_LIST_PAGE_SIZE
import com.oolyvi.yekun.utils.ApiConstants.USER_MANGA_LIST_FIELDS
import com.oolyvi.yekun.utils.ApiConstants.OFFSET_PAGE
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val malApi: MalApi,
    tokenApi: TokenApi,
    defaultPreferencesRepository: DefaultPreferencesRepository
) : BaseRepository(tokenApi, defaultPreferencesRepository) {

    suspend fun getMangaDetails(mangaId: Int): MangaDetail {
        val result = malApi.getMangaDetails(
            mangaId = mangaId,
            fields = MANGA_DETAIL_FIELDS
        )
        result.error?.let { handleResponseError(it) }
        return result
    }

    suspend fun getRankedManga(
        rankingType: String
    ): Response<MangaRanking> {
        return malApi.getMangaRanked(
            rankingType = rankingType,
            limit = COMMON_PAGE_SIZE,
            offset = OFFSET_PAGE,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            fields = SEARCH_FIELDS
        )
    }

    suspend fun searchManga(query: String): Response<MangaList> {
        return malApi.searchManga(
            query = query,
            limit = SEARCH_PAGE_SIZE,
            offset = OFFSET_PAGE,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            fields = SEARCH_FIELDS
        )
    }

    suspend fun getUserMangaList(
        status: String,
        sort: String,
    ): Response<MangaUserList> {
        val result = malApi.getUserMangaList(
            limit = USER_LIST_PAGE_SIZE,
            offset = OFFSET_PAGE,
            fields = USER_MANGA_LIST_FIELDS,
            status = status,
            nsfw = defaultPreferencesRepository.nsfwInt(),
            sort = sort
        )
        result.body()?.error?.let { handleResponseError(it) }
        return result
    }

    suspend fun updateUserMangaList(
        mangaId: Int,
        status: String? = null,
        score: Int? = null,
        numVolumesRead: Int? = null,
        numChaptersRead: Int? = null,
        isRereading: Boolean? = null,
        priority: Int? = null,
        numTimesReread: Int? = null,
        rereadValue: Int? = null,
        tags: String? = null,
        comments: String? = null,
        startDate: String? = null,
        finishDate: String? = null,
    ): MangaDetail.MyListStatus? {
        return try {
            val result = malApi.updateUserMangaList(
                mangaId = mangaId,
                status = status,
                score = score,
                numVolumesRead = numVolumesRead,
                numChaptersRead = numChaptersRead,
                isRereading = isRereading,
                priority = priority,
                numTimesReread = numTimesReread,
                rereadValue = rereadValue,
                tags = tags,
                comments = comments,
                startDate = startDate,
                finishDate = finishDate
            )
            result.error?.let { handleResponseError(it) }
            return result
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteMangaEntry(
        mangaId: Int
    ): Boolean {
        return try {
            val result = malApi.deleteMangaEntry(mangaId)
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