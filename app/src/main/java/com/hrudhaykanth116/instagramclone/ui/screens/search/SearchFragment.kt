package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.ui.adapters.SearchCategoriesAdapter
import com.hrudhaykanth116.instagramclone.databinding.SearchFragmentBinding
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: SearchFragmentBinding

    // Initial state would be loading always followed by loaded or failed
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private val searchViewModel: SearchViewModel by viewModels()

    private var fetchTopRatedTvShowsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTopRatedTvShows()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initSearchResultsRecyclerView()
        initCategoriesRecyclerView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initCategoriesRecyclerView() {

        val testList = ArrayList<String>()
        for (i in 1..60) {
            testList.add("Item: $i")
        }

        binding.categories.adapter = SearchCategoriesAdapter(testList)
        binding.categories.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun initSearchResultsRecyclerView() {
        Log.d(TAG, "initSearchResultsRecyclerView: ")

        val staggeredGridLayoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        searchResultsAdapter = SearchResultsAdapter()
        binding.searchResultsContainer.adapter = searchResultsAdapter
        binding.searchResultsContainer.layoutManager = staggeredGridLayoutManager
    }

    private fun getTopRatedTvShows(){
        Log.d(TAG, "getPopularTvShows: ")
        // Make sure we cancel the previous job before creating a new one
        fetchTopRatedTvShowsJob?.cancel()
        fetchTopRatedTvShowsJob = lifecycleScope.launchWhenStarted {
            Log.d(TAG, "getPopularTvShows: launchWhenStarted")
            searchViewModel.getTopRatedTvShows().collectLatest { tvShowPagingData: PagingData<TvShowData> ->
                Log.d(TAG, "getPopularTvShows: collectLatest")
                searchResultsAdapter.submitData(tvShowPagingData)
            }
        }
    }

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }

}
