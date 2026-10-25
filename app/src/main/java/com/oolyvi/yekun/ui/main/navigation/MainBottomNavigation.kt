package com.oolyvi.yekun.ui.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry

@Composable
fun MainBottomNavigation(
    navBackStackEntry: NavBackStackEntry,
    navActionManager: NavActionManager
) {

    // screens list
    val screens = listOf(
        BottomNavItems.Home,
        BottomNavItems.AnimeList,
        BottomNavItems.MangaList,
        BottomNavItems.More
    )
    val currentDestination = navBackStackEntry.destination


    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            SingleNavItem(
                screen = screen,
                currentDestination = currentDestination,
                navActionManager = navActionManager
            )
        }
    }

}