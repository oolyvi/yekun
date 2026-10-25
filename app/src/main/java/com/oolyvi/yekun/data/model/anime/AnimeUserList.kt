package com.oolyvi.yekun.data.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeUserList(
    val `data`: List<Data>? = null,
    val paging: Paging? = null,
    val error: String? = null
) {

    @Serializable
    data class Data(
        @SerialName("list_status")
        val listStatus: ListStatus? = null,
        val node: Node? = null
    )

    @Serializable
    data class ListStatus(
        @SerialName("is_rewatching")
        val isRewatching: Boolean? = null,
        @SerialName("num_episodes_watched")
        val numEpisodesWatched: Int? = null,
        @SerialName("num_watched_episodes")
        val numWatchedEpisodes: Int? = null,
        val score: Int? = null,
        val status: String? = null,
        @SerialName("updated_at")
        val updatedAt: String? = null,
        @SerialName("start_date")
        val startDate: String? = null,
        @SerialName("finish_date")
        val finishDate: String? = null,
        val priority: Int? = null,
        @SerialName("num_times_rewatched")
        val numTimesRewatched: Int? = null,
        @SerialName("rewatch_value")
        val rewatchValue: Int? = null,
        val tags: List<String>? = null,
        val comments: String? = null
    )

    @Serializable
    data class Node(
        val id: Int? = null,
        @SerialName("main_picture")
        val mainPicture: MainPicture? = null,
        val title: String? = null,
        @SerialName("media_type")
        val mediaType: String? = null
    )

    @Serializable
    data class MainPicture(
        val large: String? = null,
        val medium: String? = null
    )

    @Serializable
    data class Paging(
        val next: String? = null
    )

}