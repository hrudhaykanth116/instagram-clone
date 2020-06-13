package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hrudhaykanth116.instagramclone.models.MovieData

/**
 * Data factory that creates data source of popular movies.
 * Whenever data source is updated, new instance is created and returned through liveData.
 */
class PopularMoviesDataFactory: DataSource.Factory<Int, MovieData>() {

    private val TAG = PopularMoviesDataFactory::class.java.simpleName
    public val popularMovieDSLiveData: MutableLiveData<PopularMoviesDataSource> = MutableLiveData()
    private lateinit var popularMoviesDataSource: PopularMoviesDataSource

    override fun create(): DataSource<Int, MovieData> {
        Log.i(TAG, "create: ")
        popularMoviesDataSource = PopularMoviesDataSource()
        popularMovieDSLiveData.postValue(popularMoviesDataSource)
        return popularMoviesDataSource
    }

}