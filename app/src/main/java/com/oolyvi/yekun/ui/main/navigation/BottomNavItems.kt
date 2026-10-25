package com.oolyvi.yekun.ui.main.navigation

import com.oolyvi.yekun.R

/**
 * bottom navigation items
 */
sealed class BottomNavItems(
    val route: String,
    val selectedIcon: Int
) {
    data object Home : BottomNavItems(
        route = Screen.Home.route,
        selectedIcon = R.drawable.japan_house
    )

    data object AnimeList : BottomNavItems(
        route = Screen.AnimeList.route,
        selectedIcon = R.drawable.anime
    )

    data object MangaList : BottomNavItems(
        route = Screen.MangaList.route,
        selectedIcon = R.drawable.manga
    )

    data object More : BottomNavItems(
        route = Screen.More.route,
        selectedIcon = R.drawable.more
    )
}