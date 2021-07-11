package com.hrudhaykanth116.instagramclone.data.models.genres
import com.google.gson.annotations.SerializedName


data class GetTvGenresResponse(
    @SerializedName("genres")
    val genres: List<Genre>? = null
)