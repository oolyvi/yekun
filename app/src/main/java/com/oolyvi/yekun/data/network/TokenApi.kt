package com.oolyvi.yekun.data.network

import com.oolyvi.yekun.data.model.user.AccessToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenApi {

    /**
     * actual token
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("code") code: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("grant_type") grantType: String
    ): AccessToken


    /**
     * refresh token
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String
    ): AccessToken

}