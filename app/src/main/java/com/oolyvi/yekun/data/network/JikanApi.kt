package com.oolyvi.yekun.data.network

import com.oolyvi.yekun.data.model.anime.AnimeChar
import com.oolyvi.yekun.data.model.user.UserStats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {

    @GET("users/{username}/statistics")
    suspend fun getUserStatistics(
        @Path("username") username: String,
    ): UserStats?

    @GET("anime/{id}/characters")
    suspend fun getAnimeChars(
        @Path("id") id: Int,
    ): Response<AnimeChar>

}