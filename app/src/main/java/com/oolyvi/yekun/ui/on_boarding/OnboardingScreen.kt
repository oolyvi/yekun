package com.oolyvi.yekun.ui.on_boarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.on_boarding.composables.OnBoardingPagerItem
import com.oolyvi.yekun.ui.settings.SettingsViewModel
import com.oolyvi.yekun.ui.theme.ColorDarkGray
import com.oolyvi.yekun.ui.theme.ColorLightGray
import com.oolyvi.yekun.utils.singleClick

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navActionManager: NavActionManager
) {

    val viewModel: SettingsViewModel = hiltViewModel()
    val pages = listOf(
        OnBoardingUiState.FirstItem,
        OnBoardingUiState.SecondItem,
        OnBoardingUiState.ThirdItem
    )
    val pagerState = rememberPagerState(pageCount = { 3 })



    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            /**
             * horizontal pager for onboarding
             */
            HorizontalPager(
                modifier = Modifier.weight(8f),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { position ->
                OnBoardingPagerItem(onBoardingItem = pages[position])
            }


            /**
             * page indicators
             */
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) ColorDarkGray else ColorLightGray

                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(color)
                            .height(3.dp)
                            .weight(1f)
                    )
                }
            }


            /**
             * let's start button
             */
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 40.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = pagerState.currentPage == 2
                ) {
                    Button(
                        onClick = singleClick {
                            viewModel.setOnboarding(false)
                            navActionManager.toBack()
                            navActionManager.toHome()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.let_s_start),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }
    }

}