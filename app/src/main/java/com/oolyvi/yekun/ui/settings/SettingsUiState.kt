package com.oolyvi.yekun.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Immutable
import androidx.core.os.LocaleListCompat
import com.oolyvi.yekun.ui.composables.AppLanguage
import com.oolyvi.yekun.ui.composables.ThemeStyle

@Immutable
data class SettingsUiState(
    val isLoading: Boolean = false,
    val theme: ThemeStyle = ThemeStyle.FOLLOW_SYSTEM,
    val language: AppLanguage = AppLanguage.FOLLOW_SYSTEM,
    val isShownOnboarding: Boolean = true,
    val showThemeDialog: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val showNsfw: Boolean = false
) {

    companion object {
        /**
         * change app language
         */
        fun changeLocale(language: String) {
            val appLocale = if (language == "follow_system") LocaleListCompat.getEmptyLocaleList()
            else LocaleListCompat.forLanguageTags(language)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

}