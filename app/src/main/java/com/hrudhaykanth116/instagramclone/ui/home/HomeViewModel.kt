package com.hrudhaykanth116.instagramclone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.datasources.PopularTvShowsDataFactory
import java.util.concurrent.Executors


class HomeViewModel : ViewModel() {

    public var networkState: LiveData<NetworkState>
    public var popularTvShowsLiveData: LiveData<PagedList<TvShowData>>

    init {

        val popularTvShowsDataFactory = PopularTvShowsDataFactory()
        // When data source changes in factory, network state live data is also updated.
        networkState = Transformations.switchMap(popularTvShowsDataFactory.popularTvShowsLiveData) {
            it.networkState
        }

        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(7) // x items will be loaded from data source. after getting last set(last x), loadAfter() is called.
            .build()

        // x threads will be used to execute data loading one after the other.iterates again.
        val executor = Executors.newFixedThreadPool(3)
        popularTvShowsLiveData  = LivePagedListBuilder<Int, TvShowData>(popularTvShowsDataFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()


    }


}