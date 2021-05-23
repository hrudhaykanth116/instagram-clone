package com.hrudhaykanth116.instagramclone.repository.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrudhaykanth116.instagramclone.repository.models.MovieData
import com.hrudhaykanth116.instagramclone.repository.models.UserPost
import com.hrudhaykanth116.instagramclone.repository.models.User
import com.hrudhaykanth116.instagramclone.utils.typeconverters.RoomEntityConverters
import com.hrudhaykanth116.instagramclone.utils.typeconverters.RoomMovieDataConverter

@Database(entities = [User::class, UserPost::class, MovieData::class], version = 2)
@TypeConverters(RoomEntityConverters::class, RoomMovieDataConverter::class)
abstract class MyAppDb : RoomDatabase() {

    abstract fun userDao(): UserDao

}

