package com.hrudhaykanth116.instagramclone.data.models.discover
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GetDiscoverTvResponse(
    @Json(name = "page")
    val page: Int? = null,
    @Json(name = "results")
    val results: List<TvShowData>? = null,
    @Json(name = "total_pages")
    val totalPages: Int? = null,
    @Json(name = "total_results")
    val totalResults: Int? = null
) {

}
