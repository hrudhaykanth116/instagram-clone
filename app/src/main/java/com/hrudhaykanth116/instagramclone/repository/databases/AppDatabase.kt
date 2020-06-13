package com.hrudhaykanth116.instagramclone.repository.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrudhaykanth116.instagramclone.models.MovieData
import com.hrudhaykanth116.instagramclone.models.UserPost
import com.hrudhaykanth116.instagramclone.repository.daos.UserDao
import com.hrudhaykanth116.instagramclone.repository.models.User
import com.hrudhaykanth116.instagramclone.repository.typeconverters.RoomEntityConverters
import com.hrudhaykanth116.instagramclone.repository.typeconverters.RoomMovieDataConverter

@Database(entities = [User::class, UserPost::class, MovieData::class], version = 2)
@TypeConverters(RoomEntityConverters::class, RoomMovieDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "app-db"
                ).build()
            }
            return INSTANCE as AppDatabase
        }
    }

}

