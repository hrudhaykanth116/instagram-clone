package com.hrudhaykanth116.instagramclone.repository.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomMovieDataConverter {

    @TypeConverter
    fun genreIdsToString(genreIds: List<Int>): String{
        return Gson().toJson(genreIds)
    }

    @TypeConverter
    fun stringToList(jsonString: String): List<Int>{
        return  Gson().fromJson(jsonString, Array<Int>::class.java).toList()
    }

}