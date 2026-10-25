package com.oolyvi.yekun.ui.anime_detail.composables

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.anime_detail.AnimeDetailUiState
import com.oolyvi.yekun.ui.composables.lazy_items.AnimeThemeItem
import com.oolyvi.yekun.utils.Constants.URL_YOUTUBE_QUERY
import com.oolyvi.yekun.utils.buildQueryFromThemeText

@Composable
fun AnimeThemes(
    animeDetail: AnimeDetail,
    uiState: AnimeDetailUiState,
    showThemes: (Boolean) -> Unit,
    context: Context
) {

    if (!animeDetail.openingThemes.isNullOrEmpty() || !animeDetail.endingThemes.isNullOrEmpty()) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .border(0.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp))
                .clickable {
                    if (uiState.showThemes) showThemes(false) else showThemes(true)
                }
        ) {
            Text(
                text = stringResource(R.string.themes),
                fontSize = 17.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )

        }


        if (uiState.showThemes) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                /**
                 * opening themes
                 */
                animeDetail.openingThemes?.let {

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.openings),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // open theme on youtube with theme text
                    animeDetail.openingThemes.forEach { theme ->
                        theme.text?.let { it1 ->
                            AnimeThemeItem(
                                text = it1,
                                onClick = {
                                    val sharingText: Intent =
                                        Uri
                                            .parse(URL_YOUTUBE_QUERY + theme.text.buildQueryFromThemeText())
                                            .let { webpage ->
                                                Intent(Intent.ACTION_VIEW, webpage)
                                            }
                                    try {
                                        ContextCompat.startActivity(context, sharingText, null)
                                    } catch (_: ActivityNotFoundException) {

                                    }
                                }
                            )
                        }
                    }
                }
            }


            /**
             * ending themes
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                animeDetail.endingThemes?.let {

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.endings),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // open theme on youtube with theme text
                    animeDetail.endingThemes.forEach { theme ->
                        theme.text?.let { it1 ->
                            AnimeThemeItem(
                                text = it1,
                                onClick = {
                                    val sharingText: Intent =
                                        Uri
                                            .parse(URL_YOUTUBE_QUERY + theme.text.buildQueryFromThemeText())
                                            .let { webpage ->
                                                Intent(Intent.ACTION_VIEW, webpage)
                                            }
                                    try {
                                        ContextCompat.startActivity(context, sharingText, null)
                                    } catch (_: ActivityNotFoundException) {

                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}