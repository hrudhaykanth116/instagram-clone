package com.hrudhaykanth116.instagramclone.data.models.genres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GetTvGenresResponse(
    @Json(name = "genres")
    val genres: List<Genre>? = null
)