package com.hrudhaykanth116.instagramclone.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hrudhaykanth116.instagramclone.models.UserPostsData

class RoomEntityConverters {

    @TypeConverter
    fun postsToJson(userPost: UserPostsData.UserPost): String? {
        return Gson().toJson(userPost)
    }

    @TypeConverter
    fun jsonToPost(json: String?): UserPostsData.UserPost? {
        return Gson().fromJson(json, UserPostsData.UserPost::class.java)
    }

    @TypeConverter
    fun postsToJson(caption: UserPostsData.UserPost.Caption): String? {
        return Gson().toJson(caption)
    }

    @TypeConverter
    fun jsonToCaption(json: String?): UserPostsData.UserPost.Caption? {
        return Gson().fromJson(json, UserPostsData.UserPost.Caption::class.java)
    }

    @TypeConverter
    fun postsToJson(images: UserPostsData.UserPost.Images): String? {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun jsonToImages(json: String?): UserPostsData.UserPost.Images? {
        return Gson().fromJson(json, UserPostsData.UserPost.Images::class.java)
    }

    @TypeConverter
    fun postsToJson(user: UserPostsData.UserPost.User): String? {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun jsonToUser(json: String?): UserPostsData.UserPost.User? {
        return Gson().fromJson(json, UserPostsData.UserPost.User::class.java)
    }

}