package com.oolyvi.yekun.data.repository

import com.oolyvi.yekun.App
import com.oolyvi.yekun.data.network.TokenApi
import com.oolyvi.yekun.utils.Constants.MAL_API_KEY
import kotlinx.coroutines.flow.first

abstract class BaseRepository(
    private val api: TokenApi,
    val defaultPreferencesRepository: DefaultPreferencesRepository
) {

    /**
     * if token is expired or invalid, app will get refresh_token for that
     */
    suspend fun handleResponseError(error: String) {
        when (error) {
            "invalid_token" -> {
                val refreshToken = defaultPreferencesRepository.refreshToken.first()
                if (refreshToken != null) {
                    try {
                        val newToken = api.getAccessToken(
                            clientId = MAL_API_KEY,
                            refreshToken = refreshToken,
                            grantType = "refresh_token"
                        )
                        defaultPreferencesRepository.saveTokens(newToken)
                        App.ACCESS_TOKEN = newToken.accessToken
                    } catch (e: Exception) {
                        defaultPreferencesRepository.removeTokens()
                    }
                } else {
                    defaultPreferencesRepository.removeTokens()
                }
            }
        }
    }

}