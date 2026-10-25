package com.oolyvi.yekun.ui.composables

import com.oolyvi.yekun.R

/**
 * this helps to see all language locales as strings
 */
enum class AppLanguage(val value: String) {

    FOLLOW_SYSTEM("follow_system"),
    AZERBAIJANI("az"),
    ENGLISH("en"),
    HINDI("hi"),
    JAPANESE("ja"),
    KOREAN("ko"),
    PORTUGUESE("pt-PT"),
    RUSSIAN("ru-RU"),
    SPANISH("es"),
    TURKISH("tr");

    val stringResNative
        get() = when (this) {
            FOLLOW_SYSTEM -> R.string.theme_system
            ENGLISH -> R.string.english_native
            AZERBAIJANI -> R.string.azerbaijani_native
            SPANISH -> R.string.spanish_native
            HINDI -> R.string.hindi_native
            JAPANESE -> R.string.japanese_native
            KOREAN -> R.string.korean_native
            PORTUGUESE -> R.string.portuguese_native
            RUSSIAN -> R.string.russian_native
            TURKISH -> R.string.turkish_native
        }

    // localized entries
    companion object {
        fun valueOf(isoTag: String) = entries.find { it.value == isoTag }

        val entriesLocalized = entries.associateWith { it.stringResNative }
    }

}