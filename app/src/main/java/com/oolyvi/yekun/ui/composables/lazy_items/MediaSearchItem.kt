package com.oolyvi.yekun.ui.composables.lazy_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.oolyvi.yekun.data.model.anime.AnimeList
import com.oolyvi.yekun.data.model.manga.MangaList
import com.oolyvi.yekun.ui.theme.ColorStar
import com.oolyvi.yekun.utils.GeneralMediaType
import com.oolyvi.yekun.utils.debouncedClickable
import com.oolyvi.yekun.utils.getMediaTypeString

@Composable
fun MediaSearchItem(
    searchedMedia: Any,
    mediaType: GeneralMediaType,
    onItemClick: (Any) -> Unit
) {

    /**
     * data types for search item
     */
    val mediaTitle: String?
    val mediaTypeString: String?
    val mainPictureUrl: String?
    val mean: Float?

    /**
     * checking it is Anime or Manga
     */
    when (mediaType) {
        GeneralMediaType.ANIME -> {
            val anime = searchedMedia as AnimeList.Data
            mediaTitle = anime.node?.title
            mediaTypeString = getMediaTypeString(anime.node?.mediaType)
            mainPictureUrl = anime.node?.mainPicture?.large
            mean = anime.node?.mean
        }

        GeneralMediaType.MANGA -> {
            val manga = searchedMedia as MangaList.Data
            mediaTitle = manga.node?.title
            mediaTypeString = getMediaTypeString(manga.node?.mediaType)
            mainPictureUrl = manga.node?.mainPicture?.large
            mean = manga.node?.mean
        }
    }



    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .debouncedClickable(3000) {
                onItemClick(searchedMedia)
            }
            .padding(PaddingValues(start = 5.dp, end = 5.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .width(130.dp)
                .height(180.dp)
                .padding(top = 5.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            if (!mainPictureUrl.isNullOrEmpty()) {
                // main picture
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mainPictureUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .width(120.dp)
                        .height(170.dp),
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
                        .width(120.dp)
                        .height(170.dp),
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // title
                if (!mediaTitle.isNullOrEmpty()) {
                    Text(
                        text = mediaTitle.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textAlign = TextAlign.Unspecified,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // media type
                if (mediaTypeString.isNotEmpty()) {
                    Text(
                        text = mediaTypeString,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textAlign = TextAlign.Unspecified,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // mean with star icon
                if (mean != null) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$mean",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            textAlign = TextAlign.Unspecified,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = stringResource(R.string.cd_star_icon),
                            tint = ColorStar,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

