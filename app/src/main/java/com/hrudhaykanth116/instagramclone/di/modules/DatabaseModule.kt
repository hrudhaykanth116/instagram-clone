package com.hrudhaykanth116.instagramclone.di.modules

import android.content.Context
import androidx.room.Room
import com.hrudhaykanth116.instagramclone.repository.datasources.local.UserDao
import com.hrudhaykanth116.instagramclone.repository.datasources.local.MyAppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): MyAppDb {
        return Room.databaseBuilder(
            appContext,
            MyAppDb::class.java, "app-db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesUserDao(myAppDb: MyAppDb): UserDao =
        myAppDb.userDao()


}