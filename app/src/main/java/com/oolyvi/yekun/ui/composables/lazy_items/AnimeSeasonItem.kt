package com.oolyvi.yekun.ui.composables.lazy_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeSeason
import com.oolyvi.yekun.ui.theme.ColorStar
import com.oolyvi.yekun.utils.debouncedClickable

@Composable
fun AnimeSeasonItem(
    animeSeason: AnimeSeason.Data,
    onItemClick: (AnimeSeason.Data) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .debouncedClickable(3000) {
                onItemClick(animeSeason)
            }
            .padding(PaddingValues(start = 5.dp, end = 5.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .width(130.dp)
                .height(200.dp)
                .padding(top = 5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp, 142.dp)
            ) {
                if (animeSeason.node?.mainPicture != null) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(animeSeason.node.mainPicture.large)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxSize(),
                        contentDescription = stringResource(R.string.cd_media_main_picture),
                        loading = {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .scale(0.5f),
                                color = MaterialTheme.colorScheme.surface,
                                trackColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                } else {
                    // placeholder
                    Icon(
                        painter = painterResource(id = R.drawable.placeholder_image),
                        contentDescription = stringResource(R.string.cd_media_placeholder),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .width(100.dp)
                            .height(142.dp),
                    )
                }

                // star and mean
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 3.dp, start = 3.dp)
                        .width(44.dp)
                        .height(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            tint = ColorStar,
                            modifier = Modifier.size(13.dp),
                            contentDescription = stringResource(R.string.cd_star_icon)
                        )

                        if (animeSeason.node?.mean != null) {
                            Text(
                                text = "${animeSeason.node.mean}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                textAlign = TextAlign.Right,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = "-_-",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                textAlign = TextAlign.Right,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // title
                if (animeSeason.node?.title != null) {
                    Text(
                        text = animeSeason.node.title.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

}