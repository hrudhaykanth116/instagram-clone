package com.hrudhaykanth116.instagramclone.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserPost(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int,
    var caption: Caption?,
    @SerializedName("created_time")
    var createdTime: String?,
    var filter: String?,
    var id: String?,
    var images: Images?,
    var link: String?,
    var type: String?,
    var user: User?,
    @SerializedName("user_has_liked")
    var userHasLiked: Boolean?
) {
    data class Caption(
        @SerializedName("created_time")
        var createdTime: String?,
        var from: User?,
        var id: String?,
        var text: String?
    )

    data class Images(
        @SerializedName("low_resolution")
        var lowResolution: ImageDetails?,
        @SerializedName("standard_resolution")
        var standardResolution: ImageDetails?,
        var thumbnail: ImageDetails?
    ) {
        data class ImageDetails(
            var height: Int?,
            var url: String?,
            var width: Int?
        )
    }

    data class User(
        @SerializedName("full_name")
        var fullName: String?,
        var id: String?,
        @SerializedName("profile_picture")
        var profilePictureUrl: String?,
        var username: String?
    )
}