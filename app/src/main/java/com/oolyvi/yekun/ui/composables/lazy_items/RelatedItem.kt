package com.oolyvi.yekun.ui.composables.lazy_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.oolyvi.yekun.data.model.anime.AnimeDetail
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.utils.GeneralMediaType
import com.oolyvi.yekun.utils.debouncedClickable

@Composable
fun RelatedItem(
    relatedMedia: Any,
    mediaType: GeneralMediaType,
    onItemClick: (Any) -> Unit
) {

    /**
     * data types for related media item
     */
    val mediaTitle: String?
    val mainPictureUrl: String?

    /**
     * checking it is Anime or Manga
     */
    when (mediaType) {
        GeneralMediaType.ANIME -> {
            val anime = relatedMedia as AnimeDetail.RelatedAnime
            mediaTitle = anime.node?.title
            mainPictureUrl = anime.node?.mainPicture?.large
        }

        GeneralMediaType.MANGA -> {
            val manga = relatedMedia as MangaDetail.RelatedManga
            mediaTitle = manga.node?.title
            mainPictureUrl = manga.node?.mainPicture?.large
        }
    }



    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .debouncedClickable(3000) {
                onItemClick(relatedMedia)
            }
            .padding(PaddingValues(horizontal = 5.dp))
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
            if (!mainPictureUrl.isNullOrEmpty()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mainPictureUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .width(100.dp)
                        .height(142.dp),
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

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // title
                if (!mediaTitle.isNullOrEmpty()) {
                    Text(
                        text = mediaTitle.toString(),
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