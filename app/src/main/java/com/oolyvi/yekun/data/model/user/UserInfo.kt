package com.oolyvi.yekun.data.model.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    @SerialName("anime_statistics")
    val animeStatistics: AnimeStatistics? = null,
    @SerialName("joined_at")
    val joinedAt: String? = null,
    val location: String? = null,
    val name: String,
    val picture: String? = null,
    val gender: String? = null,
    val birthday: String? = null,
    val error: String? = null
) {

    @Serializable
    data class AnimeStatistics(
        @SerialName("mean_score")
        val meanScore: Float?,
        @SerialName("num_days")
        val numDays: Float?,
        @SerialName("num_days_completed")
        val numDaysCompleted: Float?,
        @SerialName("num_days_dropped")
        val numDaysDropped: Float?,
        @SerialName("num_days_on_hold")
        val numDaysOnHold: Float?,
        @SerialName("num_days_watched")
        val numDaysWatched: Float?,
        @SerialName("num_days_watching")
        val numDaysWatching: Float?,
        @SerialName("num_episodes")
        val numEpisodes: Int?,
        @SerialName("num_items")
        val numItems: Int?,
        @SerialName("num_items_completed")
        val numItemsCompleted: Int?,
        @SerialName("num_items_dropped")
        val numItemsDropped: Int?,
        @SerialName("num_items_on_hold")
        val numItemsOnHold: Int?,
        @SerialName("num_items_plan_to_watch")
        val numItemsPlanToWatch: Int?,
        @SerialName("num_items_watching")
        val numItemsWatching: Int?,
        @SerialName("num_times_rewatched")
        val numTimesRewatched: Int?
    )
}