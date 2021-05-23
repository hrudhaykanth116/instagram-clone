package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import javax.inject.Inject

/**
 * Data factory that creates data source of popular tv shows.
 * Whenever data source is updated, new instance is created and returned through liveData.
 */
class PopularTvShowsDataFactory @Inject constructor(
    private val popularTvShowsDataSource: PopularTvShowsDataSource
): DataSource.Factory<Int, TvShowData>() {

    private val TAG = PopularTvShowsDataFactory::class.java.simpleName
    public val popularTvShowsLiveData: MutableLiveData<PopularTvShowsDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, TvShowData> {
        Log.i(TAG, "create: ")
        popularTvShowsLiveData.postValue(popularTvShowsDataSource)
        return popularTvShowsDataSource
    }

    public fun invalidateDataSource(){
        popularTvShowsDataSource.invalidate()
    }


}