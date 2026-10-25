package com.oolyvi.yekun.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oolyvi.yekun.R

/**
 * get media type strings according to Anime or Manga
 */
@Composable
fun getMediaTypeString(mediaType: String?): String {
    return when (mediaType) {
        "unknown" -> stringResource(R.string.unknown)
        "tv" -> stringResource(R.string.tv)
        "ova" -> stringResource(R.string.ova)
        "movie" -> stringResource(R.string.movie)
        "special" -> stringResource(R.string.special)
        "ona" -> stringResource(R.string.ona)
        "music" -> stringResource(R.string.music)
        "manga" -> stringResource(R.string.manga)
        "novel" -> stringResource(R.string.novel)
        "one_shot" -> stringResource(R.string.one_shot)
        "doujinshi" -> stringResource(R.string.doujinshi)
        "manhwa" -> stringResource(R.string.manhwa)
        "manhua" -> stringResource(R.string.manhua)
        "oel" -> stringResource(R.string.oel)
        else -> stringResource(R.string.unknown)
    }
}