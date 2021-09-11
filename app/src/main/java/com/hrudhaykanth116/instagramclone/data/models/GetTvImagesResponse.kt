package com.hrudhaykanth116.instagramclone.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvImagesResponse(
    val backdrops: List<Backdrop?>? = null,
    val id: Int? = null,
    val posters: List<Poster?>? = null
) {

    @JsonClass(generateAdapter = true)
    data class Backdrop(
        val aspect_ratio: Double? = null,
        val file_path: String? = null,
        val height: Int? = null,
        val iso_639_1: String? = null,
        val vote_average: Double? = null,
        val vote_count: Int? = null,
        val width: Int? = null
    )

    @JsonClass(generateAdapter = true)
    data class Poster(
        val aspect_ratio: Double? = null,
        val file_path: String? = null,
        val height: Int? = null,
        val iso_639_1: String? = null,
        val vote_average: Double? = null,
        val vote_count: Int? = null,
        val width: Int? = null
    )
}