package com.hrudhaykanth116.instagramclone.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    var page: Int,
    @Json(name = "total_results")
    var totalResults: Int,
    @Json(name = "total_pages")
    var totalPages: Int,
    @Json(name = "results")
    var movieData: List<MovieData>
)