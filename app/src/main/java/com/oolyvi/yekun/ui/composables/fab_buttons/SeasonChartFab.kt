package com.oolyvi.yekun.ui.composables.fab_buttons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.oolyvi.yekun.R

@Composable
fun SeasonChartFab(
    onClick: (Boolean) -> Unit
) {

    FloatingActionButton(
        onClick = {
            onClick(true)
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sort),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.cd_filter_icon)
        )
    }

}