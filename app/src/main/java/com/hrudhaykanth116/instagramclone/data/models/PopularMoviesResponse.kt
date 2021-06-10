package com.hrudhaykanth116.instagramclone.data.models

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    var page: Int,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("results")
    var movieData: List<MovieData>
)