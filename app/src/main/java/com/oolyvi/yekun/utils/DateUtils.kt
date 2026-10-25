package com.oolyvi.yekun.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.TimeZone

/**
 * date util for start and end date for anime and manga
 */
@SuppressLint("SimpleDateFormat")
fun formatDateStartEnd(dateString: String?): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd")
    val targetFormat = SimpleDateFormat("MMM dd, yyyy")
    return try {
        val date = originalFormat.parse(dateString!!)
        date?.let { targetFormat.format(it) }
    } catch (e: Exception) {
        dateString
    }
}


/**
 * date utils for profile info
 */
fun String.toFormattedProfileBirthday(): String? {
    // use modern DateTime API (recommended for API level 26+)
    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
        val date = LocalDate.parse(this)
        return formatter.format(date)
    } else {
        // use legacy SimpleDateFormat for older API levels
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(this)
        return parsedDate?.let { outputFormat.format(it) }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun String.toFormattedJoinedAt(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM, yyyy | HH:mm", Locale.getDefault())
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) }
}


/**
 * converting dates to millis on start and end dates for anime and manga
 */
@SuppressLint("SimpleDateFormat")
fun String.toMilliseconds(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = dateFormat.parse(this)
    return date?.time ?: 0L
}


/**
 * get local date from millis
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getLocalDateFromMillis(millis: Long): LocalDate? {
    return try {
        Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
    } catch (e: Exception) {
        null
    }
}


/**
 * to localized for start and end date for anime and manga
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate?.toLocalized(
    style: FormatStyle = FormatStyle.MEDIUM
): String = try {
    this?.format(DateTimeFormatter.ofLocalizedDate(style)).orEmpty()
} catch (e: DateTimeException) {
    ""
}


/**
 * get local date from date string
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getLocalDateFromDateString(
    date: String?,
    formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
): LocalDate? {
    if (date == null) return null
    return try {
        LocalDate.parse(date, formatter)
    } catch (e: DateTimeException) {
        null
    }
}