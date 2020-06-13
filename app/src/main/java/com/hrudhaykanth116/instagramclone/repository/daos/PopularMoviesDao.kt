package com.hrudhaykanth116.instagramclone.repository.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hrudhaykanth116.instagramclone.models.MovieData

@Dao
interface PopularMoviesDao {

    @Query("SELECT * FROM movie_data")
    fun getAll(): List<MovieData>

    @Insert
    fun insertMovieData(movieDataList: List<MovieData>)

}