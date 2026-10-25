package com.oolyvi.yekun.utils

import android.net.Uri

/**
 * format the opening/ending text from MAL to use it on youtube search
 */
fun String.buildQueryFromThemeText(): String {
    // Build the query by encoding the text
    return Uri.encode(this)
}


/**
 * to string or return empty for text fields on editMediaSheet
 */
fun Any?.toStringOrEmpty() = this.toString().let { if (it == "null") "" else it }