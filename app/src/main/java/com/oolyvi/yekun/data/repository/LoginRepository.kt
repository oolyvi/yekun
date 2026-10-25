package com.oolyvi.yekun.data.repository

import com.oolyvi.yekun.data.model.user.AccessToken
import com.oolyvi.yekun.data.network.TokenApi
import com.oolyvi.yekun.utils.Constants
import com.oolyvi.yekun.utils.Constants.MAL_API_KEY
import com.oolyvi.yekun.utils.PkceGenerator
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: TokenApi,
) {

    /**
     * get access token at login
     */
    suspend fun getAccessToken(code: String): AccessToken {
        return api.getAccessToken(
            clientId = MAL_API_KEY,
            code = code,
            codeVerifier = codeVerifier,
            grantType = GRANT_TYPE
        )
    }


    /**
     * login constants
     */
    companion object LoginConstants {
        const val STATE = "yekun"
        const val GRANT_TYPE = "authorization_code"
        val codeVerifier = PkceGenerator.generateVerifier(length = 128)
        val loginUrl =
            "${Constants.URL_MAL_OAUTH2}authorize?response_type=code&client_id=${MAL_API_KEY}&code_challenge=${codeVerifier}&state=${STATE}"
    }

}