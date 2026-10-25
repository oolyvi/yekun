package com.oolyvi.yekun.data.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetail(
    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitles? = null,
    @SerialName("average_episode_duration")
    val averageEpisodeDuration: Int? = null,
    val background: String? = null,
    val broadcast: Broadcast? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("end_date")
    val endDate: String? = null,
    val genres: List<Genre>? = null,
    val id: Int? = null,
    @SerialName("main_picture")
    val mainPicture: MainPicture? = null,
    val mean: Double? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("my_list_status")
    val myListStatus: MyListStatus? = null,
    val nsfw: String? = null,
    @SerialName("num_episodes")
    val numEpisodes: Int? = null,
    @SerialName("num_list_users")
    val numListUsers: Int? = null,
    @SerialName("num_scoring_users")
    val numScoringUsers: Int? = null,
    val pictures: List<Picture>? = null,
    val popularity: Int? = null,
    val rank: Int? = null,
    val rating: String? = null,
    val recommendations: List<Recommendation>? = null,
    @SerialName("related_anime")
    val relatedAnime: List<RelatedAnime>? = null,
    val source: String? = null,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("start_season")
    val startSeason: StartSeason? = null,
    val statistics: Statistics? = null,
    val status: String? = null,
    val studios: List<Studio>? = null,
    val synopsis: String? = null,
    val title: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("opening_themes")
    val openingThemes: List<Theme>? = null,
    @SerialName("ending_themes")
    val endingThemes: List<Theme>? = null,
    val error: String? = null
) {

    @Serializable
    data class AlternativeTitles(
        val en: String? = null,
        val ja: String? = null,
        val synonyms: List<String>? = null
    )

    @Serializable
    data class Broadcast(
        @SerialName("day_of_the_week")
        val dayOfTheWeek: String? = null,
        @SerialName("start_time")
        val startTime: String? = null
    )

    @Serializable
    data class Genre(
        val id: Int? = null,
        val name: String? = null
    )

    @Serializable
    data class MainPicture(
        val large: String? = null,
        val medium: String? = null
    )

    @Serializable
    data class Node(
        val id: Int? = null,
        @SerialName("main_picture")
        val mainPicture: MainPicture? = null,
        val title: String? = null
    )

    @Serializable
    data class MyListStatus(
        val status: String? = null,
        val score: Int? = null,
        @SerialName("num_episodes_watched")
        val numEpisodesWatched: Int? = 0,
        @SerialName("is_rewatching")
        val isRewatching: Boolean? = false,
        @SerialName("start_date")
        val startDate: String? = null,
        @SerialName("finish_date")
        val finishDate: String? = null,
        val priority: Int? = 0,
        @SerialName("num_times_rewatched")
        val numTimesRewatched: Int? = 0,
        @SerialName("rewatch_value")
        val rewatchValue: Int? = 0,
        val tags: List<String>? = null,
        val comments: String? = null,
        @SerialName("updated_at")
        val updatedAt: String? = null,
        val error: String? = null
    )

    @Serializable
    data class Picture(
        val large: String? = null,
        val medium: String? = null
    )

    @Serializable
    data class Recommendation(
        val node: Node? = null,
        @SerialName("num_recommendations")
        val numRecommendations: Int? = null
    )

    @Serializable
    data class RelatedAnime(
        val node: Node? = null,
        @SerialName("relation_type")
        val relationType: String? = null,
        @SerialName("relation_type_formatted")
        val relationTypeFormatted: String? = null
    )

    @Serializable
    data class StartSeason(
        val season: String? = null,
        val year: Int? = null
    )

    @Serializable
    data class Statistics(
        @SerialName("num_list_users")
        val numListUsers: Int? = null,
        val status: Status? = null
    )

    @Serializable
    data class Status(
        val completed: Int? = null,
        val dropped: Int? = null,
        @SerialName("on_hold")
        val onHold: Int? = null,
        @SerialName("plan_to_watch")
        val planToWatch: Int? = null,
        val watching: Int? = null
    )

    @Serializable
    data class Studio(
        val id: Int? = null,
        val name: String? = null
    )

    @Serializable
    data class Theme(
        val id: Int? = null,
        @SerialName("anime_id")
        val animeId: Int? = null,
        val text: String? = null
    )
}