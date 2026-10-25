package com.oolyvi.yekun.data.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRanking(
    val `data`: List<Data>? = null,
    val paging: Paging? = null
) {

    @Serializable
    data class Data(
        val node: Node? = null,
        val ranking: Ranking? = null
    )

    @Serializable
    data class Node(
        val id: Int,
        @SerialName("main_picture")
        val mainPicture: MainPicture? = null,
        val title: String? = null,
        val mean: Float? = null
    )

    @Serializable
    data class Ranking(
        val rank: Int? = null
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