package com.oolyvi.yekun.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.ai.AiViewModel
import com.oolyvi.yekun.ui.composables.AppLanguage
import com.oolyvi.yekun.ui.composables.ThemeStyle
import com.oolyvi.yekun.ui.composables.top_bars.MainTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.more.composables.MoreCard
import com.oolyvi.yekun.ui.settings.composables.SettingsSwitch
import com.oolyvi.yekun.ui.settings.composables.LanguageDialog
import com.oolyvi.yekun.ui.settings.composables.ThemeDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navActionManager: NavActionManager
) {

    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val aiViewModel: AiViewModel = hiltViewModel()
    val settingsUiState by settingsViewModel.uiState.collectAsState()
    val aiUiState by aiViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val interactionSource = remember { MutableInteractionSource() }



    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .systemBarsPadding(),
            topBar = {
                MainTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navActionManager = navActionManager,
                    context = context,
                    topAppBarType = TopAppBarType.SETTINGS,
                    selectedRankTitle = null,
                    usernameString = null,
                    mediaDetailId = null,
                    showProfileShareDialog = { }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /**
                 * theme
                 */
                MoreCard(
                    title = stringResource(id = R.string.theme),
                    subtitle = ThemeStyle.entriesLocalized[settingsUiState.theme]?.let {
                        stringResource(
                            it
                        )
                    }.orEmpty(),
                    contentDescription = stringResource(id = R.string.cd_theme_icon),
                    icon = R.drawable.theme,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Light,
                    onClick = {
                        settingsViewModel.showThemeDialog(true)
                    }
                )

                /**
                 * language
                 */
                MoreCard(
                    title = stringResource(id = R.string.language),
                    subtitle = AppLanguage.entriesLocalized[settingsUiState.language]?.let {
                        stringResource(
                            it
                        )
                    }.orEmpty(),
                    contentDescription = stringResource(id = R.string.cd_language_icon),
                    icon = R.drawable.language,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Bold,
                    subTitleFontWeight = FontWeight.Light,
                    onClick = {
                        settingsViewModel.showLanguageDialog(true)
                    }
                )

                /**
                 * nsfw
                 */
                SettingsSwitch(
                    title = stringResource(R.string.nsfw_title),
                    subtitle = stringResource(R.string.nsfw_subtitle),
                    value = settingsUiState.showNsfw,
                    icon = R.drawable.nsfw,
                    contentDescription = stringResource(id = R.string.cd_nsfw_icon),
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    onClick = {
                        settingsViewModel.setNsfwValue(it)
                    }
                )

                /**
                 * sound on click for ai send button
                 */
                SettingsSwitch(
                    title = stringResource(R.string.sound_on_click),
                    subtitle = stringResource(R.string.activate_sound_on_ai_send_button_click),
                    value = aiUiState.isSendSoundPlayed,
                    icon = R.drawable.music,
                    contentDescription = stringResource(id = R.string.cd_music_icon),
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    onClick = {
                        aiViewModel.setIsSendSoundPlayed(it)
                    }
                )


            }
        }


        /**
         * show theme dialog
         */
        if (settingsUiState.showThemeDialog) {
            ThemeDialog(
                showThemeDialog = {
                    settingsViewModel.showThemeDialog(it)
                },
                setTheme = {
                    settingsViewModel.setTheme(it)
                },
                interactionSource = interactionSource,
                value = settingsUiState.theme
            )
        }


        /**
         * show language dialog
         */
        if (settingsUiState.showLanguageDialog) {
            LanguageDialog(
                showLanguageDialog = {
                    settingsViewModel.showLanguageDialog(it)
                },
                setLanguage = {
                    settingsViewModel.setLanguage(it)
                },
                interactionSource = interactionSource,
                value = settingsUiState.language
            )
        }
    }

}