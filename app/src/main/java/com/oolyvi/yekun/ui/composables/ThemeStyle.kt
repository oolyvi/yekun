package com.oolyvi.yekun.ui.composables

import com.oolyvi.yekun.R

/**
 * this helps to see all kinds of theme values
 */
enum class ThemeStyle {
    FOLLOW_SYSTEM, LIGHT, DARK;

    val stringRes
        get() = when (this) {
            FOLLOW_SYSTEM -> R.string.theme_system
            LIGHT -> R.string.theme_light
            DARK -> R.string.theme_dark
        }

    companion object {
        fun valueOfOrNull(value: String) = try {
            valueOf(value)
        } catch (e: IllegalArgumentException) {
            null
        }

        val entriesLocalized = entries.associateWith { it.stringRes }
    }
}