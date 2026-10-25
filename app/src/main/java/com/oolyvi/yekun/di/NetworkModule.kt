package com.oolyvi.yekun.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.oolyvi.yekun.data.network.JikanApi
import com.oolyvi.yekun.data.network.MalApi
import com.oolyvi.yekun.data.network.OAuthInterceptor
import com.oolyvi.yekun.data.network.TokenApi
import com.oolyvi.yekun.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val networkJson = Json { ignoreUnknownKeys = true }


    @Provides
    @Singleton
    fun provideMalApi(okHttpClient: OkHttpClient): MalApi {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE_MAL)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MalApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJikanApi(): JikanApi {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE_JIKAN)
            .client(OkHttpClient())
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(JikanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccessToken(okHttpClient: OkHttpClient): TokenApi {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_MAL_OAUTH2)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TokenApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor())
            .addInterceptor(interceptor).build()
    }
}