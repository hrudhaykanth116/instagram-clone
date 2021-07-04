package com.hrudhaykanth116.instagramclone.data.models.search
import com.google.gson.annotations.SerializedName
import com.hrudhaykanth116.instagramclone.data.models.TvShowData

data class TvShowSearchResults(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val tvShowDataList: List<TvShowData?>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)