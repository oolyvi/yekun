package com.oolyvi.yekun.data.model.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStats(
    val `data`: Data
) {

    @Serializable
    data class Data(
        val anime: Anime,
        val manga: Manga
    )

    @Serializable
    data class Anime(
        val completed: Int,
        @SerialName("days_watched")
        val daysWatched: Float,
        val dropped: Int,
        @SerialName("episodes_watched")
        val episodesWatched: Int,
        @SerialName("mean_score")
        val meanScore: Float,
        @SerialName("on_hold")
        val onHold: Int,
        @SerialName("plan_to_watch")
        val planToWatch: Int,
        val rewatched: Int,
        @SerialName("total_entries")
        val totalEntries: Int,
        val watching: Int
    )

    @Serializable
    data class Manga(
        @SerialName("chapters_read")
        val chaptersRead: Int,
        val completed: Int,
        @SerialName("days_read")
        val daysRead: Float,
        val dropped: Int,
        @SerialName("mean_score")
        val meanScore: Float,
        @SerialName("on_hold")
        val onHold: Int,
        @SerialName("plan_to_read")
        val planToRead: Int,
        val reading: Int,
        val reread: Int,
        @SerialName("total_entries")
        val totalEntries: Int,
        @SerialName("volumes_read")
        val volumesRead: Int
    )
}