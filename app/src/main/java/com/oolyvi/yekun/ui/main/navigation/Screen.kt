package com.oolyvi.yekun.ui.main.navigation

sealed class Screen(
    val route: String
) {

    /**
     * onboarding
     */
    data object Onboarding : Screen("onboarding")


    /**
     * home
     */
    data object Home : Screen("home")
    data object FullForYou : Screen("full_for_you")
    data object SeasonChart : Screen("season_chart")
    data object AnimeRanking : Screen("anime_ranking")
    data object Ai : Screen("ai")
    data object Search : Screen("search")
    data object MangaRanking : Screen("manga_ranking")


    /**
     * anime list
     */
    data object AnimeList : Screen("anime_list")


    /**
     * manga list
     */
    data object MangaList : Screen("manga_list")


    /**
     * more
     */
    data object More : Screen("more")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object About : Screen("about")
    data object Credits : Screen("credits")


    /**
     * anime detail
     */
    data object AnimeDetail : Screen("anime_detail")
    data object AnimePictures : Screen("anime_pictures")


    /**
     * manga detail
     */
    data object MangaDetail : Screen("manga_detail")
    data object MangaPictures : Screen("manga_pictures")

}