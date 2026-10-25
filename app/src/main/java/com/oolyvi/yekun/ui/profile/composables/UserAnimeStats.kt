package com.oolyvi.yekun.ui.profile.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.user.UserStats
import com.oolyvi.yekun.ui.more.MoreUiState
import java.text.NumberFormat

@Composable
fun UserAnimeStats(
    uiState: MoreUiState,
    userStatistics: UserStats,
    changeAnimationPlayed: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // watching, completed ... counts
        if ((userStatistics.data?.anime?.watching != null) && (userStatistics.data.anime.completed != null) &&
            (userStatistics.data.anime.onHold != null) && (userStatistics.data.anime.dropped != null) &&
            (userStatistics.data.anime.planToWatch != null)
            && (userStatistics.data.anime.totalEntries != null)
        ) {
            UserWatchingStatsChart(
                data = mapOf(
                    Pair(stringResource(R.string.watching), userStatistics.data.anime.watching),
                    Pair(stringResource(R.string.completed), userStatistics.data.anime.completed),
                    Pair(stringResource(R.string.on_hold), userStatistics.data.anime.onHold),
                    Pair(stringResource(R.string.dropped), userStatistics.data.anime.dropped),
                    Pair(
                        stringResource(R.string.plan_to_watch),
                        userStatistics.data.anime.planToWatch
                    ),
                ),
                uiState = uiState,
                changeAnimationPlayed = changeAnimationPlayed
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // general stats ... counts
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (userStatistics.data?.anime?.daysWatched != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.today),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(id = R.string.cd_date_range_icon)
                        )

                        Text(
                            text = stringResource(R.string.days),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = NumberFormat.getNumberInstance()
                            .format(userStatistics.data.anime.daysWatched),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (userStatistics.data?.anime?.episodesWatched != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.play_arrow),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(id = R.string.cd_play_icon)
                        )

                        Text(
                            text = stringResource(id = R.string.episodes),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = NumberFormat.getNumberInstance()
                            .format(userStatistics.data.anime.episodesWatched),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (userStatistics.data?.anime?.meanScore != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(id = R.string.cd_star_icon)
                        )

                        Text(
                            text = stringResource(R.string.mean_score),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = NumberFormat.getNumberInstance()
                            .format(userStatistics.data.anime.meanScore),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (userStatistics.data?.anime?.rewatched != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.repeat),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(id = R.string.cd_repeat_icon)
                        )

                        Text(
                            text = stringResource(R.string.rewatched),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = NumberFormat.getNumberInstance()
                            .format(userStatistics.data.anime.rewatched),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

    }

}