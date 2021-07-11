package com.hrudhaykanth116.instagramclone.data.models.discover
import com.google.gson.annotations.SerializedName


data class GetDiscoverTvResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<DiscoverResult>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
) {

}
