package com.oolyvi.yekun.data.model.manga


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaDetail(
    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitles? = null,
    val authors: List<Author>? = null,
    val background: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val genres: List<Genre>? = null,
    val id: Int,
    @SerialName("main_picture")
    val mainPicture: MainPicture? = null,
    val mean: Double? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("my_list_status")
    val myListStatus: MyListStatus? = null,
    val nsfw: String? = null,
    @SerialName("num_chapters")
    val numChapters: Int? = null,
    @SerialName("num_list_users")
    val numListUsers: Int? = null,
    @SerialName("num_scoring_users")
    val numScoringUsers: Int? = null,
    @SerialName("num_volumes")
    val numVolumes: Int? = null,
    val pictures: List<Picture>? = null,
    val popularity: Int? = null,
    val rank: Int? = null,
    val recommendations: List<Recommendation>? = null,
    @SerialName("related_manga")
    val relatedManga: List<RelatedManga>? = null,
    @SerialName("serialization")
    val serialization: List<Serialization>? = null,
    @SerialName("start_date")
    val startDate: String? = null,
    val status: String? = null,
    val synopsis: String? = null,
    val title: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    val error: String? = null
) {

    @Serializable
    data class AlternativeTitles(
        val en: String? = null,
        val ja: String? = null,
        val synonyms: List<String>? = null
    )

    @Serializable
    data class Author(
        val node: Node? = null,
        val role: String? = null
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
    data class MyListStatus(
        @SerialName("is_rereading")
        val isRereading: Boolean? = false,
        @SerialName("num_chapters_read")
        val numChaptersRead: Int? = null,
        @SerialName("num_volumes_read")
        val numVolumesRead: Int? = null,
        @SerialName("start_date")
        val startDate: String? = null,
        @SerialName("finish_date")
        val finishDate: String? = null,
        val score: Int? = null,
        val status: String? = null,
        @SerialName("updated_at")
        val updatedAt: String? = null,
        val priority: Int? = null,
        @SerialName("num_times_reread")
        val numTimesReread: Int? = null,
        @SerialName("reread_value")
        val rereadValue: Int? = 0,
        val tags: List<String>? = null,
        val comments: String? = null,
        val error: String? = null
    )

    @Serializable
    data class Node(
        @SerialName("first_name")
        val firstName: String? = null,
        val id: Int? = null,
        @SerialName("last_name")
        val lastName: String? = null
    )

    @Serializable
    data class NodeX(
        val id: Int? = null,
        @SerialName("main_picture")
        val mainPicture: MainPicture? = null,
        val title: String? = null
    )

    @Serializable
    data class NodeXXX(
        val id: Int? = null,
        val name: String? = null
    )

    @Serializable
    data class Picture(
        val large: String? = null,
        val medium: String? = null
    )

    @Serializable
    data class Recommendation(
        val node: NodeX? = null,
        @SerialName("num_recommendations")
        val numRecommendations: Int? = null
    )

    @Serializable
    data class RelatedManga(
        val node: NodeX? = null,
        @SerialName("relation_type")
        val relationType: String? = null,
        @SerialName("relation_type_formatted")
        val relationTypeFormatted: String? = null
    )

    @Serializable
    data class Serialization(
        val node: NodeXXX? = null
    )
}