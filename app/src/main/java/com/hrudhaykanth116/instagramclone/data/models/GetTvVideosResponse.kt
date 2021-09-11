package com.hrudhaykanth116.instagramclone.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvVideosResponse(
    val id: Int? = null,
    val results: List<Result?>? = null
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        val id: String? = null,
        val iso_3166_1: String? = null,
        val iso_639_1: String? = null,
        val key: String? = null,
        val name: String? = null,
        val official: Boolean? = null,
        val published_at: String? = null,
        val site: String? = null,
        val size: Int? = null,
        val type: String? = null
    )
}