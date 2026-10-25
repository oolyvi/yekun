package com.oolyvi.yekun.ui.composables.top_bars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomButton
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMediumTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navActionManager: NavActionManager,
    topAppBarType: TopAppBarType,
    selectedSeason: String?,
    selectedYear: Int?
) {

    val sortedSelectedSeason = when (selectedSeason) {
        "winter" -> stringResource(R.string.winter)
        "spring" -> stringResource(R.string.spring)
        "summer" -> stringResource(R.string.summer)
        "fall" -> stringResource(R.string.fall)
        else -> stringResource(id = R.string.spring)
    }



    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            if (topAppBarType == TopAppBarType.FULL_FOR_YOU || topAppBarType == TopAppBarType.SEASON_CHART) {
                /**
                 * top bar title
                 */
                val topBarTitle = if (topAppBarType == TopAppBarType.FULL_FOR_YOU) {
                    stringResource(id = R.string.for_you)
                } else {
                    "${
                        sortedSelectedSeason.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }
                    } $selectedYear"
                }

                Text(
                    text = topBarTitle,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            CustomButton(
                icon = R.drawable.arrow_back,
                iconSize = 25.dp,
                iconTint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.cd_back_icon),
                isIconButton = true,
                onClick = navActionManager::toBack
            )
        },
        scrollBehavior = scrollBehavior
    )

}