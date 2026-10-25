package com.oolyvi.yekun.ui.main.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.theme.ColorTransparent
import com.oolyvi.yekun.utils.bottomIconBounceClick

/**
 * each screen element
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleNavItem(
    screen: BottomNavItems,
    currentDestination: NavDestination?,
    navActionManager: NavActionManager
) {

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val interactionSource = remember { MutableInteractionSource() }



    Box(
        modifier = Modifier
            .clip(CircleShape)
            .bottomIconBounceClick()
            .combinedClickable(
                onClick = {
                    navActionManager.toBottomItem(screen.route)
                },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Column(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val badgeColor = if (selected) MaterialTheme.colorScheme.primary else ColorTransparent

            Icon(
                painter = painterResource(id = screen.selectedIcon),
                contentDescription = stringResource(R.string.cd_bottom_navigation_icon),
                tint = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    0.5f
                ),
                modifier = Modifier.size(27.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // badge will shown up, when screen is selected
            Badge(containerColor = badgeColor, modifier = Modifier.size(7.dp))
        }
    }

}