package com.oolyvi.yekun.ui.more

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.App
import com.oolyvi.yekun.data.repository.DefaultPreferencesRepository
import com.oolyvi.yekun.data.repository.LoginRepository
import com.oolyvi.yekun.data.repository.LoginRepository.LoginConstants.STATE
import com.oolyvi.yekun.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoreUiState())
    val uiState: StateFlow<MoreUiState> = _uiState.asStateFlow()

    val accessToken = defaultPreferencesRepository.accessToken


    init {
        getUserInfoAndStats()
    }


    // get token from authorization via API and parse it
    fun parseIntentData(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val code = uri.getQueryParameter("code")
                val receivedState = uri.getQueryParameter("state")

                val tokenResponse = if (code != null && receivedState == STATE) {
                    loginRepository.getAccessToken(code)
                } else {
                    null
                }

                if (tokenResponse != null) {
                    tokenResponse.accessToken?.let { Log.d("the token is:", it) }
                    defaultPreferencesRepository.saveTokens(tokenResponse)
                    App.ACCESS_TOKEN = tokenResponse.accessToken
                }
            } catch (e: Exception) {
                e.message?.let { Log.d("parseIntentData", it) }
            }
        }
    }


    // if user log out
    suspend fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            defaultPreferencesRepository.removeTokens()
            App.ACCESS_TOKEN = null
        }
    }


    // get user info from mal and stats from jikan
    fun getUserInfoAndStats() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val user = userRepository.getUser()

            val userStats = user?.name?.let {
                userRepository.getUserStatistics(
                    username = it.lowercase()
                )
            }

            _uiState.update {
                it.copy(
                    userInfo = user,
                    userStatistics = userStats,
                    errorMessage = if (user != null) null else "Something went wrong!",
                    isLoading = false
                )
            }
        }
    }


    /**
     * set loading
     */
    fun setLoading(value: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = value
            )
        }
    }


    /**
     * change animation played for watching stats
     */
    fun onChangeAnimationPlayed(value: Boolean) {
        _uiState.update {
            it.copy(isStatsAnimationPlayed = value)
        }
    }


    /**
     * show and hide picture dialogs
     */
    fun showPictureDialog(value: Boolean) {
        _uiState.update {
            it.copy(showPictureDialog = value)
        }
    }

    fun showPictureDialogFull(value: Boolean) {
        _uiState.update {
            it.copy(showPictureDialogFull = value)
        }
    }


    /**
     * show log out dialog
     */
    fun showLogOutDialog(value: Boolean) {
        _uiState.update {
            it.copy(showLogOutDialog = value)
        }
    }


    /**
     * show share mal profile dialog
     */
    fun showShareDialog(value: Boolean) {
        _uiState.update {
            it.copy(showShareDialog = value)
        }
    }

}