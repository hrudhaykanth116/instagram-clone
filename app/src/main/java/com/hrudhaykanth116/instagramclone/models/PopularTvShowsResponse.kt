package com.hrudhaykanth116.instagramclone.models
import com.google.gson.annotations.SerializedName


data class PopularTvShowsResponse(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val tvShowsList: List<TvShowData> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)

