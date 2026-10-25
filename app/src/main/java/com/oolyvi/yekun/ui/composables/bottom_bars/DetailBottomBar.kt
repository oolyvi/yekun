package com.oolyvi.yekun.ui.composables.bottom_bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MarkAsUnread
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.theme.ColorCompleted
import com.oolyvi.yekun.ui.theme.ColorDropped
import com.oolyvi.yekun.ui.theme.ColorOnHold
import com.oolyvi.yekun.ui.theme.ColorPlanToWatch
import com.oolyvi.yekun.ui.theme.ColorWatching
import com.oolyvi.yekun.utils.GeneralMediaType

@Composable
fun DetailBottomBar(
    onClick: (Boolean) -> Unit,
    generalMediaType: GeneralMediaType,
    selectedStatus: String?
) {

    /**
     * status icon for anime and manga
     */
    val statusIcon = if (generalMediaType == GeneralMediaType.MANGA) {
        when (selectedStatus) {
            "reading" -> Icons.Default.MarkAsUnread
            "completed" -> Icons.Default.Done
            "on_hold" -> Icons.Default.Pause
            "dropped" -> Icons.Default.Delete
            "plan_to_read" -> Icons.Default.WatchLater
            else -> Icons.Default.Add
        }
    } else {
        when (selectedStatus) {
            "watching" -> Icons.Default.PlayArrow
            "completed" -> Icons.Default.Done
            "on_hold" -> Icons.Default.Pause
            "dropped" -> Icons.Default.Delete
            "plan_to_watch" -> Icons.Default.WatchLater
            else -> Icons.Default.Add
        }
    }

    /**
     * status title for anime and manga
     */
    val statusTitle = if (generalMediaType == GeneralMediaType.MANGA) {
        when (selectedStatus) {
            "reading" -> stringResource(R.string.reading)
            "completed" -> stringResource(R.string.completed)
            "on_hold" -> stringResource(R.string.on_hold)
            "dropped" -> stringResource(R.string.dropped)
            "plan_to_read" -> stringResource(R.string.plan_to_read)
            else -> stringResource(R.string.add_to_list)
        }
    } else {
        when (selectedStatus) {
            "watching" -> stringResource(R.string.watching)
            "completed" -> stringResource(R.string.completed)
            "on_hold" -> stringResource(R.string.on_hold)
            "dropped" -> stringResource(R.string.dropped)
            "plan_to_watch" -> stringResource(R.string.plan_to_watch)
            else -> stringResource(R.string.add_to_list)
        }
    }

    /**
     * status button color for anime and manga
     */
    val statusButtonColor = if (generalMediaType == GeneralMediaType.MANGA) {
        when (selectedStatus) {
            "reading" -> ColorWatching
            "completed" -> ColorCompleted
            "plan_to_read" -> ColorPlanToWatch
            "on_hold" -> ColorOnHold
            "dropped" -> ColorDropped
            else -> {
                MaterialTheme.colorScheme.primary
            }
        }
    } else {
        when (selectedStatus) {
            "watching" -> ColorWatching
            "completed" -> ColorCompleted
            "plan_to_watch" -> ColorPlanToWatch
            "on_hold" -> ColorOnHold
            "dropped" -> ColorDropped
            else -> {
                MaterialTheme.colorScheme.primary
            }
        }
    }



    Button(
        onClick = {
            onClick(true)
        },
        colors = ButtonColors(
            containerColor = statusButtonColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = statusButtonColor,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 5.dp, top = 10.dp)
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // user list status icon
            Icon(
                imageVector = statusIcon,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = stringResource(R.string.cd_selected_status_icon),
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            // user list status title
            Text(
                text = statusTitle.uppercase(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp
            )
        }
    }

}