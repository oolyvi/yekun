package com.oolyvi.yekun.ui.on_boarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.oolyvi.yekun.R

sealed class OnBoardingUiState(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    data object FirstItem : OnBoardingUiState(
        image = R.drawable.airi_katagiri,
        title = R.string.onboarding_title_1,
        description = R.string.onboarding_subtitle_1
    )

    data object SecondItem : OnBoardingUiState(
        image = R.drawable.ai_girl,
        title = R.string.onboarding_title_2,
        description = R.string.onboarding_subtitle_2
    )

    data object ThirdItem : OnBoardingUiState(
        image = R.drawable.death_note,
        title = R.string.onboarding_title_3,
        description = R.string.onboarding_subtitle_3
    )
}