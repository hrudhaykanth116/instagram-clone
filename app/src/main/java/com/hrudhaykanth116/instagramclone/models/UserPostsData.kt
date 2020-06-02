package com.hrudhaykanth116.instagramclone.models

import com.google.gson.annotations.SerializedName

data class UserPostsData(
    @SerializedName("user_posts")
    var userPostsPages: List<List<UserPost>>?
)