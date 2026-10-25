package com.oolyvi.yekun.ui.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.utils.shimmerEffect

/**
 * home shimmer
 */
@Composable
fun ShimmerHome() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 5.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..25) {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .width(100.dp)
                        .height(142.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .width(100.dp)
                        .height(30.dp)
                        .shimmerEffect()
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }

}


/**
 * detail shimmer
 */
@Composable
fun ShimmerDetail() {

    val configuration = LocalConfiguration.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 70.dp,
                bottom = 10.dp
            )
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // main picture header
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .height(280.dp)
                .width(204.dp)
                .shimmerEffect()
                .padding(horizontal = 5.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // name
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .weight(0.1f)
                .width(configuration.screenWidthDp.dp)
                .shimmerEffect()
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(15.dp))

        // genres
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .weight(0.1f)
                .width(configuration.screenWidthDp.dp / 1.5f)
                .shimmerEffect()
                .align(Alignment.Start)
                .padding(horizontal = 5.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // synopsis
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .weight(0.3f)
                .width(configuration.screenWidthDp.dp)
                .shimmerEffect()
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(15.dp))

        // info
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(configuration.screenWidthDp.dp)
                .weight(0.5f)
                .shimmerEffect()
                .align(Alignment.CenterHorizontally),
        )
    }

}

//@Preview
//@Composable
//private fun ShimmerDetailPreview() {
//    ShimmerDetail()
//}
//
//@Preview
//@Composable
//private fun ShimmerHomePreview() {
//    ShimmerHome()
//}