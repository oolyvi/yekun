package com.oolyvi.yekun.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.oolyvi.yekun.App.Companion.ACCESS_TOKEN
import com.oolyvi.yekun.data.repository.DefaultPreferencesRepository
import com.oolyvi.yekun.ui.composables.ThemeStyle
import com.oolyvi.yekun.ui.main.navigation.MainNavigation
import com.oolyvi.yekun.ui.main.navigation.NavActionManager.Companion.rememberNavActionManager
import com.oolyvi.yekun.ui.main.navigation.Screen
import com.oolyvi.yekun.ui.more.MoreViewModel
import com.oolyvi.yekun.ui.settings.SettingsViewModel
import com.oolyvi.yekun.ui.theme.YekunTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * some instances from other parts of app
     */
    @Inject
    lateinit var defaultPreferencesRepository: DefaultPreferencesRepository

    private val moreViewModel: MoreViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /**
         * initialize splash screen and edge to edge
         */
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
        )


        /**
         * get token value after intent
         */
        if (intent.data?.toString()?.startsWith("oolyvi://yekunapp.com/") == true) {
            intent.data?.let {
                moreViewModel.parseIntentData(it)
            }
        }

        // access token value
        ACCESS_TOKEN = runBlocking {
            moreViewModel.accessToken.first()
        }

        // initial value of theme
        val initialTheme = runBlocking {
            settingsViewModel.theme.first()
        }

        // initial value of onboarding
        val initialOnboarding = runBlocking {
            settingsViewModel.onBoarding.first()
        }


        setContent {
            val accessToken by moreViewModel.accessToken.collectAsState(ACCESS_TOKEN)
            val navController = rememberNavController()
            val navActionManager = rememberNavActionManager(navController)
            val theme by settingsViewModel.theme.collectAsState(initial = initialTheme)
            val isDarkTheme = if (theme == ThemeStyle.FOLLOW_SYSTEM) isSystemInDarkTheme()
            else theme == ThemeStyle.DARK
            val onBoarding by settingsViewModel.onBoarding.collectAsState(initial = initialOnboarding)
            val isShownOnboarding =
                if (onBoarding) Screen.Onboarding.route else Screen.Home.route


            YekunTheme(
                darkTheme = isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    MainNavigation(
                        navController = navController,
                        isLoggedIn = !accessToken.isNullOrEmpty(),
                        navActionManager = navActionManager,
                        startDestination = isShownOnboarding
                    )
                }
            }
        }

    }


    /**
     * get token value on new intent
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.data?.toString()?.startsWith("oolyvi://yekunapp.com/") == true) {
            intent.data?.let {
                moreViewModel.parseIntentData(it)
            }
        }
    }

}