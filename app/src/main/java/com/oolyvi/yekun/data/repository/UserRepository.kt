package com.oolyvi.yekun.data.repository

import com.oolyvi.yekun.data.model.user.UserInfo
import com.oolyvi.yekun.data.model.user.UserStats
import com.oolyvi.yekun.data.network.JikanApi
import com.oolyvi.yekun.data.network.MalApi
import com.oolyvi.yekun.data.network.TokenApi
import com.oolyvi.yekun.utils.ApiConstants.USER_FIELDS
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val jikanApi: JikanApi,
    private val malApi: MalApi,
    tokenApi: TokenApi,
    defaultPreferencesRepository: DefaultPreferencesRepository
) : BaseRepository(tokenApi, defaultPreferencesRepository) {

    suspend fun getUser(): UserInfo? {
        return try {
            val result = malApi.getUser(
                fields = USER_FIELDS
            )
            result.error?.let { handleResponseError(it) }
            return result
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserStatistics(username: String): UserStats? {
        return try {
            jikanApi.getUserStatistics(username)
        } catch (e: Exception) {
            null
        }
    }

}