package com.oolyvi.yekun.ui.credits

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.top_bars.MainTopAppBar
import com.oolyvi.yekun.ui.composables.top_bars.TopAppBarType
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.more.composables.MoreCard
import com.oolyvi.yekun.ui.theme.ColorPrimary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditsScreen(
    navActionManager: NavActionManager
) {

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current



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
                    topAppBarType = TopAppBarType.CREDITS,
                    selectedRankTitle = null,
                    usernameString = null,
                    mediaDetailId = null,
                    showProfileShareDialog = { }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 40.dp)
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                /**
                 * general help
                 */
                Text(
                    text = stringResource(R.string.special_thanks),
                    color = ColorPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                MoreCard(
                    title = null,
                    subtitle = "@axiel7",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = ColorPrimary,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://github.com/axiel7")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                MoreCard(
                    title = null,
                    subtitle = "@Shamchiyev",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = ColorPrimary,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://www.linkedin.com/in/murad-shamchiyev-299801287/")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * MAL API help
                 */
                Text(
                    text = stringResource(R.string.myanimelist_api_help),
                    color = ColorPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                MoreCard(
                    title = null,
                    subtitle = "@ZeroCrystal",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = ColorPrimary,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://myanimelist.net/profile/ZeroCrystal")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * logo design
                 */
                Text(
                    text = stringResource(R.string.logo_design),
                    color = ColorPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                MoreCard(
                    title = null,
                    subtitle = "@oolyvi",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = ColorPrimary,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://github.com/oolyvi")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * translations
                 */
                Text(
                    text = stringResource(R.string.translations),
                    color = ColorPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                // azerbaijani
                MoreCard(
                    title = stringResource(R.string.azerbaijani),
                    subtitle = "@oolyvi",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://www.linkedin.com/in/oolyvi/")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                // hindi
                MoreCard(
                    title = stringResource(R.string.hindi),
                    subtitle = "@pranshul",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {

                    }
                )

                // japanese
                MoreCard(
                    title = stringResource(R.string.japanese_),
                    subtitle = "@Rossam",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://x.com/4_mio_11")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                // korean
                MoreCard(
                    title = stringResource(R.string.korean),
                    subtitle = "@핫바꼬치",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://www.twitch.tv/1_ise/about")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                // portuguese
                MoreCard(
                    title = stringResource(R.string.portuguese_portugal),
                    subtitle = "@lighttigerXIV",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://github.com/lighttigerXIV")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                // russian
                MoreCard(
                    title = stringResource(R.string.russian_russia),
                    subtitle = "@Shamchiyev",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://www.linkedin.com/in/murad-shamchiyev-299801287/")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                // spanish
                MoreCard(
                    title = stringResource(R.string.spanish),
                    subtitle = "@axiel7",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://github.com/axiel7")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                // turkish
                MoreCard(
                    title = stringResource(R.string.turkish),
                    subtitle = "@melih_uzunoglu",
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {
                        val sharingText: Intent =
                            Uri
                                .parse("https://github.com/melihuzunoglu")
                                .let { webpage ->
                                    Intent(Intent.ACTION_VIEW, webpage)
                                }
                        try {
                            ContextCompat.startActivity(context, sharingText, null)
                        } catch (_: ActivityNotFoundException) {

                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * testers
                 */
                Text(
                    text = stringResource(R.string.testers),
                    color = ColorPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                // tester names sorting by alphabetically   count == 29
                MoreCard(
                    title = "@adityaapandeyy, @akshaynighot73, @axiel7, @dandiwalpb03, @erwinelder, @felipefreitassilva, " +
                            "@french1613, @gruzin, @hentaiboy_bandera, @jinxed19, @jimmy04080, @jordan_jakisa, @lelestacia, " +
                            "@lighttigerxiv, @l0rdvoldemort, @melih_uzunoglu, @moaliyou, @mr.vigneshkumar, @nobuya0627, @offhandgames, " +
                            "@pranshul, @sakthee_i, @secondsogyy, @Shamchiyev, @stecco4423, @varquhaz, @vienle, @warforged, @yeeyson",
                    subtitle = null,
                    contentDescription = null,
                    icon = null,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitleColor = MaterialTheme.colorScheme.onSurface,
                    titleFontWeight = FontWeight.Normal,
                    subTitleFontWeight = FontWeight.Normal,
                    onClick = {

                    }
                )

            }
        }
    }

}