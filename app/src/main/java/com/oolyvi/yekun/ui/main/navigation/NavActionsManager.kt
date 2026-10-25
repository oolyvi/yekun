package com.oolyvi.yekun.ui.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * this class holds all directions of this application
 */
@Immutable
class NavActionManager(
    private val navController: NavHostController
) {

    fun toBottomItem(item: String) {
        navController.navigateSingleTopTo(item)
    }

    fun toBack() {
        navController.popBackStack()
    }

    fun toHome() {
        navController.navigate(Screen.Home.route)
    }

    fun toAnimeDetails(id: Int) {
        navController.navigate(Screen.AnimeDetail.route + "/${id}")
    }

    fun toMangaDetails(id: Int) {
        navController.navigate(Screen.MangaDetail.route + "/${id}")
    }

    fun toAnimePictures(id: Int) {
        navController.navigateSingleTopTo2(Screen.AnimePictures.route + "/${id}")
    }

    fun toMangaPictures(id: Int) {
        navController.navigateSingleTopTo2(Screen.MangaPictures.route + "/${id}")
    }

    fun toSeasonChart() {
        navController.navigateSingleTopTo(Screen.SeasonChart.route)
    }

    fun toFullRecommendations() {
        navController.navigateSingleTopTo(Screen.FullForYou.route)
    }

    fun toSearch() {
        navController.navigate(Screen.Search.route)
    }

    fun toAi() {
        navController.navigate(Screen.Ai.route)
    }

    fun toSettings() {
        navController.navigateSingleTopTo2(Screen.Settings.route)
    }

    fun toAbout() {
        navController.navigateSingleTopTo2(Screen.About.route)
    }

    fun toCredits() {
        navController.navigateSingleTopTo2(Screen.Credits.route)
    }

    fun toAnimeRanking() {
        navController.navigateSingleTopTo(Screen.AnimeRanking.route)
    }

    fun toMangaRanking() {
        navController.navigateSingleTopTo(Screen.MangaRanking.route)
    }

    fun toTopAnime(id: Int) {
        navController.navigateSingleTopTo2(Screen.AnimeDetail.route + "/${id}")
    }

    fun toTopManga(id: Int) {
        navController.navigateSingleTopTo2(Screen.MangaDetail.route + "/${id}")
    }

    fun toProfile() {
        navController.navigateSingleTopTo2(Screen.Profile.route)
    }


    companion object {

        /**
         * remember nav action manager for using NavActionsManager
         */
        @Composable
        fun rememberNavActionManager(
            navController: NavHostController = rememberNavController()
        ) = remember {
            NavActionManager(navController)
        }


        /**
         * helps to avoid multiple and doesn't return back to previous screen
         */
        fun NavController.navigateSingleTopTo(route: String) =
            this.navigate(route) {
                popUpTo(
                    this@navigateSingleTopTo.graph.findStartDestination().id
                ) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }


        /**
         * helps to avoid multiple and this will return back to previous screen
         */
        fun NavController.navigateSingleTopTo2(route: String) =
            this.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }

    }

}