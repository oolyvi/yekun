package com.oolyvi.yekun.data.network

import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.data.model.anime.AnimeForYou
import com.oolyvi.yekun.data.model.anime.AnimeList
import com.oolyvi.yekun.data.model.anime.AnimeRanking
import com.oolyvi.yekun.data.model.anime.AnimeSeason
import com.oolyvi.yekun.data.model.anime.AnimeUserList
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.data.model.manga.MangaList
import com.oolyvi.yekun.data.model.manga.MangaRanking
import com.oolyvi.yekun.data.model.manga.MangaUserList
import com.oolyvi.yekun.data.model.user.UserInfo
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface MalApi {

    /**
     * general anime
     */
    @GET("anime/season/{year}/{season}")
    suspend fun getSeasonAnime(
        @Path("year") year: Int,
        @Path("season") season: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("sort") sort: String,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String?
    ): Response<AnimeSeason>

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") animeId: Int,
        @Query("fields") fields: String
    ): AnimeDetail

    @GET("anime/ranking")
    suspend fun getAnimeRanked(
        @Query("ranking_type") rankingType: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String?
    ): Response<AnimeRanking>

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String,
    ): Response<AnimeList>


    /**
     * general manga
     */
    @GET("manga/{manga_id}")
    suspend fun getMangaDetails(
        @Path("manga_id") mangaId: Int,
        @Query("fields") fields: String
    ): MangaDetail

    @GET("manga/ranking")
    suspend fun getMangaRanked(
        @Query("ranking_type") rankingType: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String?
    ): Response<MangaRanking>

    @GET("manga")
    suspend fun searchManga(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String,
    ): Response<MangaList>


    /**
     * anime with logged in
     */
    @GET("users/@me")
    suspend fun getUser(
        @Query("fields") fields: String
    ): UserInfo

    @GET("anime/suggestions")
    suspend fun getAnimeForYou(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String
    ): Response<AnimeForYou>


    @GET("users/@me/animelist")
    suspend fun getUserAnimeList(
        @Query("status") status: String,
        @Query("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String?
    ): Response<AnimeUserList>

    @PATCH("anime/{anime_id}/my_list_status")
    @FormUrlEncoded
    suspend fun updateUserAnimeList(
        @Path("anime_id") animeId: Int,
        @Field("status") status: String?,
        @Field("score") score: Int?,
        @Field("num_watched_episodes") numWatchedEpisodes: Int?,
        @Field("is_rewatching") isRewatching: Boolean?,
        @Field("start_date") startDate: String?,
        @Field("finish_date") finishDate: String?,
        @Field("priority") priority: Int?,
        @Field("num_times_rewatched") numTimesRewatched: Int?,
        @Field("rewatch_value") rewatchValue: Int?,
        @Field("tags") tags: String?,
        @Field("comments") comments: String?
    ): AnimeDetail.MyListStatus

    @DELETE("anime/{anime_id}/my_list_status")
    suspend fun deleteAnimeEntry(
        @Path("anime_id") animeId: Int
    ): Response<Unit>


    /**
     * manga with logged in
     */
    @GET("users/@me/mangalist")
    suspend fun getUserMangaList(
        @Query("status") status: String,
        @Query("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nsfw") nsfw: Int,
        @Query("fields") fields: String?
    ): Response<MangaUserList>

    @PATCH("manga/{manga_id}/my_list_status")
    @FormUrlEncoded
    suspend fun updateUserMangaList(
        @Path("manga_id") mangaId: Int,
        @Field("status") status: String?,
        @Field("is_rereading") isRereading: Boolean?,
        @Field("score") score: Int?,
        @Field("num_volumes_read") numVolumesRead: Int?,
        @Field("num_chapters_read") numChaptersRead: Int?,
        @Field("priority") priority: Int?,
        @Field("num_times_reread") numTimesReread: Int?,
        @Field("reread_value") rereadValue: Int?,
        @Field("tags") tags: String?,
        @Field("comments") comments: String?,
        @Field("start_date") startDate: String?,
        @Field("finish_date") finishDate: String?
    ): MangaDetail.MyListStatus

    @DELETE("manga/{manga_id}/my_list_status")
    suspend fun deleteMangaEntry(
        @Path("manga_id") mangaId: Int
    ): Response<Unit>

}