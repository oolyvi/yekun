package com.oolyvi.yekun.ui.main.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.oolyvi.yekun.ui.about.AboutScreen
import com.oolyvi.yekun.ui.ai.AiScreen
import com.oolyvi.yekun.ui.anime_detail.AnimeDetailScreen
import com.oolyvi.yekun.ui.anime_detail.composables.AnimePicturesScreen
import com.oolyvi.yekun.ui.anime_list.AnimeListScreen
import com.oolyvi.yekun.ui.anime_ranking.AnimeRankingScreen
import com.oolyvi.yekun.ui.credits.CreditsScreen
import com.oolyvi.yekun.ui.home.HomeScreen
import com.oolyvi.yekun.ui.home.composables.FullForYouScreen
import com.oolyvi.yekun.ui.manga_detail.MangaDetailScreen
import com.oolyvi.yekun.ui.manga_detail.composables.MangaPicturesScreen
import com.oolyvi.yekun.ui.manga_list.MangaListScreen
import com.oolyvi.yekun.ui.manga_ranking.MangaRankingScreen
import com.oolyvi.yekun.ui.more.MoreScreen
import com.oolyvi.yekun.ui.on_boarding.OnboardingScreen
import com.oolyvi.yekun.ui.profile.ProfileScreen
import com.oolyvi.yekun.ui.search.SearchScreen
import com.oolyvi.yekun.ui.season_chart.SeasonChartScreen
import com.oolyvi.yekun.ui.settings.SettingsScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainNavigation(
    navController: NavHostController,
    navActionManager: NavActionManager,
    startDestination: String,
    isLoggedIn: Boolean
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()



    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(tween(400)) },
        exitTransition = { fadeOut(tween(400)) },
        popEnterTransition = { fadeIn(tween(400)) },
        popExitTransition = { fadeOut(tween(400)) }
    ) {
        /**
         * on boarding
         */
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                navActionManager = navActionManager
            )
        }

        /**
         * anime detail
         */
        composable(
            route = "${Screen.AnimeDetail.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            AnimeDetailScreen(
                isLoggedIn = isLoggedIn,
                navActionManager = navActionManager
            )
        }


        /**
         * manga detail
         */
        composable(
            route = "${Screen.MangaDetail.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            MangaDetailScreen(
                isLoggedIn = isLoggedIn,
                navActionManager = navActionManager
            )
        }


        /**
         * anime pictures
         */
        composable(
            route = "${Screen.AnimePictures.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            }
        ) {
            AnimePicturesScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * manga pictures
         */
        composable(
            route = "${Screen.MangaPictures.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            }
        ) {
            MangaPicturesScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * home
         */
        composable(
            route = Screen.Home.route
        ) {
            navBackStackEntry?.let { it1 ->
                HomeScreen(
                    isLoggedIn = isLoggedIn,
                    navActionManager = navActionManager,
                    navBackStackEntry = it1
                )
            }
        }


        /**
         * season chart
         */
        composable(
            route = Screen.SeasonChart.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            SeasonChartScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * full for you
         */
        composable(
            route = Screen.FullForYou.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            FullForYouScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * anime ranking
         */
        composable(
            route = Screen.AnimeRanking.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            AnimeRankingScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * manga ranking
         */
        composable(
            route = Screen.MangaRanking.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            MangaRankingScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * gemini ai
         */
        composable(
            route = Screen.Ai.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            AiScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * search
         */
        composable(
            route = Screen.Search.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            SearchScreen(
                navActionManager = navActionManager
            )
        }


        /**
         * anime list
         */
        composable(
            route = Screen.AnimeList.route
        ) {
            navBackStackEntry?.let { it1 ->
                AnimeListScreen(
                    isLoggedIn = isLoggedIn,
                    navActionManager = navActionManager,
                    navBackStackEntry = it1
                )
            }
        }


        /**
         * manga list
         */
        composable(
            route = Screen.MangaList.route
        ) {
            navBackStackEntry?.let { it1 ->
                MangaListScreen(
                    isLoggedIn = isLoggedIn,
                    navActionManager = navActionManager,
                    navBackStackEntry = it1
                )
            }
        }


        /**
         * more
         */
        composable(
            route = Screen.More.route
        ) {
            navBackStackEntry?.let { it1 ->
                MoreScreen(
                    isLoggedIn = isLoggedIn,
                    navActionManager = navActionManager,
                    navBackStackEntry = it1
                )
            }
        }


        /**
         * profile
         */
        composable(
            route = Screen.Profile.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            ProfileScreen(
                isLoggedIn = isLoggedIn,
                navActionManager = navActionManager
            )
        }


        /**
         * settings
         */
        composable(
            route = Screen.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            SettingsScreen(
                navActionManager = navActionManager,
            )
        }


        /**
         * about
         */
        composable(
            route = Screen.About.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            AboutScreen(
                navActionManager = navActionManager,
            )
        }


        /**
         * credits
         */
        composable(
            route = Screen.Credits.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(
                        300
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            CreditsScreen(
                navActionManager = navActionManager,
            )
        }

    }
}