package com.hrudhaykanth116.instagramclone.models

import com.google.gson.annotations.SerializedName

data class UserPostsData(
    var userPosts: List<UserPost?>?
) {
    data class UserPost(
        var attribution: Any?,
        var caption: Caption?,
        @SerializedName("created_time")
        var createdTime: String?,
        var filter: String?,
        var id: String?,
        var images: Images?,
        var link: String?,
        var location: Any?,
        var tags: List<Any?>?,
        var type: String?,
        var user: User?,
        @SerializedName("user_has_liked")
        var userHasLiked: Boolean?,
        @SerializedName("users_in_photo")
        var usersInPhoto: List<Any?>?
    ) {
        data class Caption(
            @SerializedName("created_time")
            var createdTime: String?,
            var from: From?,
            var id: String?,
            var text: String?
        ) {
            data class From(
                @SerializedName("full_name")
                var fullName: String?,
                var id: String?,
                @SerializedName("profile_picture")
                var profilePicture: String?,
                var username: String?
            )
        }

        data class Images(
            @SerializedName("low_resolution")
            var lowResolution: LowResolution?,
            @SerializedName("standard_resolution")
            var standardResolution: StandardResolution?,
            var thumbnail: Thumbnail?
        ) {
            data class LowResolution(
                var height: Int?,
                var url: String?,
                var width: Int?
            )

            data class StandardResolution(
                var height: Int?,
                var url: String?,
                var width: Int?
            )

            data class Thumbnail(
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
            var profilePicture: String?,
            var username: String?
        )
    }
}