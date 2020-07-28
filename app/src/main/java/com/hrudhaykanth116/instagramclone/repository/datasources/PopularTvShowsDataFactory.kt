package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hrudhaykanth116.instagramclone.models.TvShowData

/**
 * Data factory that creates data source of popular tv shows.
 * Whenever data source is updated, new instance is created and returned through liveData.
 */
class PopularTvShowsDataFactory: DataSource.Factory<Int, TvShowData>() {

    private val TAG = PopularTvShowsDataFactory::class.java.simpleName
    public val popularTvShowsLiveData: MutableLiveData<PopularTvShowsDataSource> = MutableLiveData()
    private lateinit var popularTvShowsDataSource: PopularTvShowsDataSource

    override fun create(): DataSource<Int, TvShowData> {
        Log.i(TAG, "create: ")
        popularTvShowsDataSource = PopularTvShowsDataSource()
        popularTvShowsLiveData.postValue(popularTvShowsDataSource)
        return popularTvShowsDataSource
    }

    public fun invalidateDataSource(){
        popularTvShowsDataSource.invalidate()
    }


}