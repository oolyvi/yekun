package com.oolyvi.yekun.data.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeChar(
    val `data`: List<Data>? = null
) {

    @Serializable
    data class Data(
        val character: Character? = null,
        val role: String? = null,
        @SerialName("voice_actors")
        val voiceActors: List<VoiceActor>? = null
    )

    @Serializable
    data class Character(
        val images: Images? = null,
        @SerialName("mal_id")
        val malId: Int? = null,
        val name: String? = null,
        val url: String? = null
    )

    @Serializable
    data class Images(
        val jpg: Jpg? = null,
        val webp: Webp? = null
    )

    @Serializable
    data class ImagesX(
        val jpg: JpgX? = null
    )

    @Serializable
    data class Jpg(
        @SerialName("image_url")
        val imageUrl: String? = null,
        @SerialName("small_image_url")
        val smallImageUrl: String? = null
    )

    @Serializable
    data class JpgX(
        @SerialName("image_url")
        val imageUrl: String? = null
    )

    @Serializable
    data class Person(
        val images: ImagesX? = null,
        @SerialName("mal_id")
        val malId: Int? = null,
        val name: String? = null,
        val url: String? = null
    )

    @Serializable
    data class VoiceActor(
        val language: String? = null,
        val person: Person? = null
    )

    @Serializable
    data class Webp(
        @SerialName("image_url")
        val imageUrl: String? = null,
        @SerialName("small_image_url")
        val smallImageUrl: String? = null
    )
}