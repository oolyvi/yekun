package com.oolyvi.yekun.data.model.manga


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaUserList(
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
        @SerialName("is_rereading")
        val isRereading: Boolean? = null,
        @SerialName("num_chapters_read")
        val numChaptersRead: Int? = null,
        @SerialName("num_volumes_read")
        val numVolumesRead: Int? = null,
        val score: Int? = null,
        val status: String? = null,
        @SerialName("updated_at")
        val updatedAt: String? = null,
        val priority: Int? = null,
        @SerialName("num_times_reread")
        val numTimesReread: Int? = null,
        @SerialName("reread_value")
        val rereadValue: Int? = null,
        val tags: List<String>? = null,
        val comments: String? = null
    )

    @Serializable
    data class Node(
        val id: Int,
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