package com.hrudhaykanth116.instagramclone.repository.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrudhaykanth116.instagramclone.models.UserPostsData
import com.hrudhaykanth116.instagramclone.repository.RoomEntityConverters
import com.hrudhaykanth116.instagramclone.repository.daos.UserDao
import com.hrudhaykanth116.instagramclone.repository.models.User

@Database(entities = [User::class, UserPostsData.UserPost::class], version = 1)
@TypeConverters(RoomEntityConverters::class)
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

// Example to receive the db instance
