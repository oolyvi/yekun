package com.oolyvi.yekun.ui.composables.fab_buttons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.oolyvi.yekun.R
import com.oolyvi.yekun.utils.GeneralMediaType

@Composable
fun UserListFab(
    onClick: (Boolean) -> Unit,
    generalMediaType: GeneralMediaType,
    selectedStatusIcon: String?
) {


    val statusIcon = if (generalMediaType == GeneralMediaType.MANGA) {
        when (selectedStatusIcon) {
            "reading" -> R.drawable.book
            "completed" -> R.drawable.done
            "on_hold" -> R.drawable.pause
            "dropped" -> R.drawable.delete
            "plan_to_read" -> R.drawable.watch_later
            else -> R.drawable.done
        }
    } else {
        when (selectedStatusIcon) {
            "watching" -> R.drawable.play_arrow
            "completed" -> R.drawable.done
            "on_hold" -> R.drawable.pause
            "dropped" -> R.drawable.delete
            "plan_to_watch" -> R.drawable.watch_later
            else -> R.drawable.done
        }
    }



    FloatingActionButton(
        onClick = {
            onClick(true)
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(id = statusIcon),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.cd_selected_status_icon)
        )
    }

}