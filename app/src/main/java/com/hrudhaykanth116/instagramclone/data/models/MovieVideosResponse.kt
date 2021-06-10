package com.hrudhaykanth116.instagramclone.data.models


import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("results")
    val results: List<MovieVideo?>? = listOf()
)

