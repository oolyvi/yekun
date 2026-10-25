package com.oolyvi.yekun.ui.anime_detail.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.ui.main.navigation.NavActionManager

@Composable
fun AnimePicturePoster(
    animeDetail: AnimeDetail,
    navActionManager: NavActionManager
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        if (animeDetail.mainPicture != null) {
            // anime image
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(animeDetail.mainPicture.large)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .height(280.dp)
                    .width(204.dp)
                    .clickable {
                        animeDetail.id?.let { navActionManager.toAnimePictures(it) }
                    },
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
            // anime placeholder image
            IconButton(
                onClick = { },
                modifier = Modifier
                    .height(280.dp)
                    .width(204.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = stringResource(R.string.cd_media_placeholder),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .height(220.dp)
                        .width(160.dp),
                )
            }
        }
    }

}