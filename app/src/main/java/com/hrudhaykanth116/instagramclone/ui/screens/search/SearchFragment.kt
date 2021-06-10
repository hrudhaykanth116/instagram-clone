package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.databinding.SearchFragmentBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.SearchCategoriesAdapter
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

        initViews()
        getTopRatedTvShows()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        initClickListeners()
        initSearchResultsRecyclerView()
        initCategoriesRecyclerView()
    }

    private fun initClickListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        searchResultsAdapter.refresh()
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

        searchResultsAdapter.addLoadStateListener{ combinedLoadStates ->
            onLoadStateChanged(combinedLoadStates)
        }

    }

    private fun onLoadStateChanged(combinedLoadStates: CombinedLoadStates) {

        if (
            (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.prepend.endOfPaginationReached) ||
            combinedLoadStates.source.refresh is LoadState.Error
        ) {

            // FIRST LOAD COMPLETED(either success or error)

            binding.swipeRefreshLayout.isRefreshing = false
            // binding.shimmerFrameLayout.stopShimmer()
            // binding.shimmerFrameLayout.isGone = true
        }

        // Not loading and no error(Data loaded). Show empty list
        val isListEmpty =
            combinedLoadStates.refresh is LoadState.NotLoading &&
                    combinedLoadStates.prepend.endOfPaginationReached &&
                    searchResultsAdapter.itemCount == 0

        binding.noDataTextView.isVisible = isListEmpty


        binding.dataLoadErrorView.isVisible =
            combinedLoadStates.source.refresh is LoadState.Error
                    && searchResultsAdapter.itemCount < 1

        val refreshDataLoadError = combinedLoadStates.source.refresh as? LoadState.Error?
        refreshDataLoadError?.let {
            Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT).show()
        }

        // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
        val errorState = combinedLoadStates.source.append as? LoadState.Error
            ?: combinedLoadStates.source.prepend as? LoadState.Error
            ?: combinedLoadStates.append as? LoadState.Error
            ?: combinedLoadStates.prepend as? LoadState.Error

        errorState?.let {
            Log.e(TAG, "onLoadStateChanged: error: ", it.error)
        }

    }

    private fun getTopRatedTvShows(){
        Log.d(TAG, "getPopularTvShows: ")
        // Make sure we cancel the previous job before creating a new one
        fetchTopRatedTvShowsJob?.cancel()
        fetchTopRatedTvShowsJob = lifecycleScope.launchWhenStarted {
            Log.d(TAG, "getPopularTvShows: launchWhenStarted")
            searchViewModel.getTopRatedTvShows().collectLatest { tvShowPagingData: PagingData<TvShowData> ->
                Log.d(TAG, "getPopularTvShows: collectLatest")
                binding.swipeRefreshLayout.isRefreshing = false
                searchResultsAdapter.submitData(tvShowPagingData)
            }
        }
    }

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }

}
