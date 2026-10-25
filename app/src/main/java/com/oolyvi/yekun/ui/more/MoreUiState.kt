package com.oolyvi.yekun.ui.more

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Immutable
import com.oolyvi.yekun.data.model.user.UserInfo
import com.oolyvi.yekun.data.model.user.UserStats
import com.oolyvi.yekun.ui.theme.ColorMoreStats1
import com.oolyvi.yekun.ui.theme.ColorMoreStats2
import com.oolyvi.yekun.ui.theme.ColorMoreStats3
import com.oolyvi.yekun.ui.theme.ColorMoreStats4
import com.oolyvi.yekun.ui.theme.ColorMoreStats5
import com.oolyvi.yekun.utils.toFormattedJoinedAt
import com.oolyvi.yekun.utils.toFormattedProfileBirthday

@Immutable
data class MoreUiState(
    val userInfo: UserInfo? = null,
    val userStatistics: UserStats? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showPictureDialog: Boolean = false,
    val showPictureDialogFull: Boolean = false,
    val isStatsAnimationPlayed: Boolean = false,
    val showLogOutDialog: Boolean = false,
    val showShareDialog: Boolean = false
) {

    val userStatsColors = listOf(
        ColorMoreStats1,
        ColorMoreStats2,
        ColorMoreStats3,
        ColorMoreStats4,
        ColorMoreStats5
    )

    val formattedBirthday = userInfo?.birthday?.toFormattedProfileBirthday()

    @RequiresApi(Build.VERSION_CODES.N)
    val formattedJoinDate = userInfo?.joinedAt?.toFormattedJoinedAt()
}