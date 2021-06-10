package com.hrudhaykanth116.instagramclone.utils.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hrudhaykanth116.instagramclone.data.models.UserPost

class RoomEntityConverters {

    @TypeConverter
    fun postsToJson(caption: UserPost.Caption): String? {
        return Gson().toJson(caption)
    }

    @TypeConverter
    fun jsonToCaption(json: String?): UserPost.Caption? {
        return Gson().fromJson(json, UserPost.Caption::class.java)
    }

    @TypeConverter
    fun postsToJson(images: UserPost.Images): String? {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun jsonToImages(json: String?): UserPost.Images? {
        return Gson().fromJson(json, UserPost.Images::class.java)
    }

    @TypeConverter
    fun postsToJson(user: UserPost.User): String? {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun jsonToUser(json: String?): UserPost.User? {
        return Gson().fromJson(json, UserPost.User::class.java)
    }

}