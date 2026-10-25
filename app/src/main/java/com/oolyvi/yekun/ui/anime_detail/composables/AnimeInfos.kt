package com.oolyvi.yekun.ui.anime_detail.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.anime_detail.AnimeDetailUiState
import com.oolyvi.yekun.ui.composables.custom_buttons.CustomTextButton
import java.util.Locale

@Composable
fun AnimeInfos(
    animeDetail: AnimeDetail,
    clipboardManager: ClipboardManager,
    uiState: AnimeDetailUiState,
    context: Context
) {

    /**
     * anim source mapping
     */
    val animeSource = when (animeDetail.source) {
        "other" -> stringResource(R.string.other)
        "original" -> stringResource(R.string.original)
        "manga" -> stringResource(R.string.manga)
        "4_koma_manga" -> stringResource(R.string._4_koma_manga)
        "web_manga" -> stringResource(R.string.web_manga)
        "digital_manga" -> stringResource(R.string.digital_manga)
        "novel" -> stringResource(R.string.novel)
        "light_novel" -> stringResource(R.string.light_novel)
        "visual_novel" -> stringResource(R.string.visual_novel)
        "game" -> stringResource(R.string.game)
        "card_game" -> stringResource(R.string.card_game)
        "book" -> stringResource(R.string.book)
        "picture_book" -> stringResource(R.string.picture_book)
        "radio" -> stringResource(R.string.radio)
        "music" -> stringResource(R.string.music)
        else -> stringResource(R.string.unknown)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.info),
            fontSize = 17.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 15.dp)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // rank
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.rank),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.rank != null) {
                    Text(
                        text = "# ${uiState.formattedRank}",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // popularity
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.popularity),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.popularity != null) {
                    Text(
                        text = uiState.formattedPopularity,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // scoring users
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.scoring_users),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.numScoringUsers != null) {
                    Text(
                        text = uiState.formattedNumScoringUser,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // number of list user
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.list_users),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.numListUsers != null) {
                    Text(
                        text = uiState.formattedNumListUser,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // synonyms
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.synonyms),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!animeDetail.alternativeTitles?.synonyms.isNullOrEmpty()) {
                    animeDetail.alternativeTitles?.synonyms?.let {
                        Text(
                            text = it.joinToString(separator = ", "),
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // japanese title
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.japanese),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!animeDetail.alternativeTitles?.ja.isNullOrEmpty()) {
                    animeDetail.alternativeTitles?.ja?.let {
                        CustomTextButton(
                            title = it,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 10,
                            overflow = TextOverflow.Ellipsis,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            onClick = {
                                clipboardManager.setText(AnnotatedString((animeDetail.alternativeTitles.ja)))
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.japanese_title_was_copied),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // english title
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.english),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!animeDetail.alternativeTitles?.en.isNullOrEmpty()) {
                    animeDetail.alternativeTitles?.en?.let {
                        CustomTextButton(
                            title = it,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 10,
                            overflow = TextOverflow.Ellipsis,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            onClick = {
                                clipboardManager.setText(AnnotatedString((animeDetail.alternativeTitles.en)))
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.english_title_was_copied),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // start date
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.start_date),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!uiState.formattedStartDate.isNullOrEmpty()) {
                    Text(
                        text = uiState.formattedStartDate,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // end date
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.end_date),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!uiState.formattedEndDate.isNullOrEmpty()) {
                    Text(
                        text = uiState.formattedEndDate,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // start season
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.season),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.startSeason != null) {
                    Text(
                        text = "${
                            animeDetail.startSeason.season.toString().replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            }
                        } ${animeDetail.startSeason.year}",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // broadcast
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.broadcast),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.broadcast != null) {
                    Text(
                        text = "${
                            animeDetail.broadcast.dayOfTheWeek.toString().replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            }
                        } ${animeDetail.broadcast.startTime}",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // duration
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.duration),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.averageEpisodeDuration != null) {
                    Text(
                        text = if (animeDetail.averageEpisodeDuration > 3600 && uiState.remainingMinutes != 0) {
                            "${uiState.hours} h ${uiState.remainingMinutes} min"
                        } else if (animeDetail.averageEpisodeDuration % 3600 == 0 || uiState.remainingMinutes == 0) {
                            "${uiState.hours} h"
                        } else {
                            "${uiState.durationWithMinute} min"
                        },
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.source),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!animeSource.isNullOrEmpty()) {
                    Text(
                        text = animeSource,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // studios
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.studios),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!animeDetail.studios.isNullOrEmpty()) {
                    Text(
                        text = animeDetail.studios.joinToString(separator = ", ") { it.name.toString() },
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // number of episodes
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.episodes_count),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (animeDetail.numEpisodes != null) {
                    Text(
                        text = "${animeDetail.numEpisodes}",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "-_-",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

}