package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import javax.inject.Inject

class TopRatedTvShowsDataFactory @Inject constructor(
    private val topRatedTvShowsDataSource: TopRatedTvShowsDataSource
): DataSource.Factory<Int, TvShowData>() {

    private val TAG = TopRatedTvShowsDataFactory::class.java.simpleName
    public val topRatedTvShowsDataSourceLiveData: MutableLiveData<TopRatedTvShowsDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, TvShowData> {
        Log.i(TAG, "create: ")
        topRatedTvShowsDataSourceLiveData.postValue(topRatedTvShowsDataSource)
        return topRatedTvShowsDataSource
    }
}