package com.oolyvi.yekun.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.repository.DefaultPreferencesRepository
import com.oolyvi.yekun.ui.composables.AppLanguage
import com.oolyvi.yekun.ui.composables.ThemeStyle
import com.oolyvi.yekun.ui.settings.SettingsUiState.Companion.changeLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val theme = defaultPreferencesRepository.theme
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeStyle.FOLLOW_SYSTEM)

    val onBoarding = defaultPreferencesRepository.onboarding


    init {
        getTheme()
        getOnBoarding()
        getNsfwValue()
        getLanguage()
    }


    /**
     * set and get theme value
     */
    fun setTheme(value: ThemeStyle) {
        viewModelScope.launch {
            defaultPreferencesRepository.setTheme(value)
        }
    }

    private fun getTheme() {
        defaultPreferencesRepository.theme
            .onEach { value ->
                _uiState.update { it.copy(theme = value) }
            }
            .launchIn(viewModelScope)
    }


    /**
     * get and set language value
     */
    fun setLanguage(value: AppLanguage) {
        viewModelScope.launch {
            defaultPreferencesRepository.setLanguage(value)
            changeLocale(value.value)
        }
    }

    private fun getLanguage() {
        defaultPreferencesRepository.language
            .onEach { value ->
                _uiState.update { it.copy(language = value) }
            }
            .launchIn(viewModelScope)
    }


    /**
     * set and get onboarding value
     */
    fun setOnboarding(value: Boolean) {
        viewModelScope.launch {
            defaultPreferencesRepository.setOnboarding(value)
        }
    }

    private fun getOnBoarding() {
        defaultPreferencesRepository.onboarding
            .onEach { value ->
                _uiState.update { it.copy(isShownOnboarding = value) }
            }
            .launchIn(viewModelScope)
    }


    /**
     * set and get nsfw value
     */
    fun setNsfwValue(value: Boolean) {
        viewModelScope.launch {
            defaultPreferencesRepository.setNsfw(value)
        }
    }

    private fun getNsfwValue() {
        defaultPreferencesRepository.nsfw
            .onEach { value ->
                _uiState.update { it.copy(showNsfw = value) }
            }
            .launchIn(viewModelScope)
    }


    /**
     * show theme dialog
     */
    fun showThemeDialog(value: Boolean) {
        _uiState.update {
            it.copy(showThemeDialog = value)
        }
    }


    /**
     * show language dialog
     */
    fun showLanguageDialog(value: Boolean) {
        _uiState.update {
            it.copy(showLanguageDialog = value)
        }
    }

}