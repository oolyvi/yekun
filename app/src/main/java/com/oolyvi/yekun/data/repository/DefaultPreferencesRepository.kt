package com.oolyvi.yekun.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.oolyvi.yekun.data.model.user.AccessToken
import com.oolyvi.yekun.ui.composables.AppLanguage
import com.oolyvi.yekun.ui.composables.ThemeStyle
import com.oolyvi.yekun.utils.toInt
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    /**
     * ACCESS_TOKEN and REFRESH_TOKEN
     */
    val accessToken = dataStore.getValue(ACCESS_TOKEN_KEY)
    val refreshToken = dataStore.getValue(REFRESH_TOKEN_KEY)

    suspend fun saveTokens(value: AccessToken) {
        dataStore.edit {
            if (value.accessToken != null) it[ACCESS_TOKEN_KEY] = value.accessToken
            if (value.refreshToken != null) it[REFRESH_TOKEN_KEY] = value.refreshToken
        }
    }

    suspend fun removeTokens() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN_KEY)
            it.remove(REFRESH_TOKEN_KEY)
        }
    }


    /**
     * theme
     */
    val theme = dataStore.getValue(THEME_KEY, ThemeStyle.FOLLOW_SYSTEM.name)
        .map { ThemeStyle.valueOfOrNull(it) ?: ThemeStyle.FOLLOW_SYSTEM }

    suspend fun setTheme(value: ThemeStyle) {
        dataStore.setValue(THEME_KEY, value.name)
    }


    /**
     * language
     */
    val language = dataStore.getValue(LANGUAGE_KEY, AppLanguage.FOLLOW_SYSTEM.value)
        .map { AppLanguage.valueOf(isoTag = it) ?: AppLanguage.FOLLOW_SYSTEM }

    suspend fun setLanguage(value: AppLanguage) {
        dataStore.setValue(LANGUAGE_KEY, value.value)
    }


    /**
     * onboarding
     */
    val onboarding = dataStore.getValue(ONBOARDING_KEY, true)

    suspend fun setOnboarding(value: Boolean) {
        dataStore.setValue(ONBOARDING_KEY, value)
    }


    /**
     * nsfw
     */
    val nsfw = dataStore.getValue(NSFW_KEY, false)
    suspend fun nsfwInt() = nsfw.first().toInt()
    suspend fun setNsfw(value: Boolean) {
        dataStore.setValue(NSFW_KEY, value)
    }


    /**
     * send button click sound
     */
    val sendClickSoundPlayed = dataStore.getValue(SEND_CLICK_SOUND_PLAYED_KEY, false)

    suspend fun setSendClickSoundPlayed(value: Boolean) {
        dataStore.setValue(SEND_CLICK_SOUND_PLAYED_KEY, value)
    }


    /************************************
     * getters & setters
     */
    fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>) = data.map { it[key] }

    fun <T> DataStore<Preferences>.getValue(
        key: Preferences.Key<T>,
        default: T,
    ) = data.map { it[key] ?: default }

    suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T?
    ) = edit {
        if (value != null) it[key] = value
        else it.remove(key)
    }


    /************************************
     * preference keys
     */
    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

        val THEME_KEY = stringPreferencesKey("theme")
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val NSFW_KEY = booleanPreferencesKey("nsfw")
        val ONBOARDING_KEY = booleanPreferencesKey("onboarding")
        val SEND_CLICK_SOUND_PLAYED_KEY = booleanPreferencesKey("send_click_sound")
        const val DEFAULT_DATA_STORE = "default"
    }

}