package com.hrudhaykanth116.instagramclone.data.repository.datasources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrudhaykanth116.instagramclone.data.models.MovieData
import com.hrudhaykanth116.instagramclone.data.models.User
import com.hrudhaykanth116.instagramclone.data.models.UserPost
import com.hrudhaykanth116.instagramclone.data.repository.datasources.local.room.daos.UserDao
import com.hrudhaykanth116.instagramclone.utils.typeconverters.RoomEntityConverters
import com.hrudhaykanth116.instagramclone.utils.typeconverters.RoomMovieDataConverter

@Database(entities = [User::class, UserPost::class, MovieData::class], version = 2)
@TypeConverters(RoomEntityConverters::class, RoomMovieDataConverter::class)
abstract class MyAppDb : RoomDatabase() {

    abstract fun userDao(): UserDao

}

