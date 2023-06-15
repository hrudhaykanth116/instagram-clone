package com.hrudhaykanth116.instagramclone.ui.screens.profile.similar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.hrudhaykanth116.instagramclone.data.constants.AppConstants
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.databinding.FragmentTvSimilarShowsBinding
import com.hrudhaykanth116.instagramclone.ui.common.list.setGridLayout
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.ui.screens.search.SearchResultsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TvSimilarShowFragment : BaseFragment() {

    private var fetchTopRatedTvShowsJob: Job? = null

    private val searchResultsAdapter by lazy {
        SearchResultsAdapter(){
            // TODO: 13/09/21 Implement this
        }
    }
    lateinit var binding: FragmentTvSimilarShowsBinding
    private val viewModel: TvSimilarShowsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvSimilarShowsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<TvShowDetails>(KEY_TV_SHOW_DETAILS)
                ?.let { tvShowDetails: TvShowDetails ->
                    refreshDiscoverTvShows(tvShowDetails.id!!)
                }
        }
        initViews()
    }

    private fun initViews() {
        initSearchResultsRecyclerView()
    }

    private fun initSearchResultsRecyclerView() {
        Log.d(TAG, "initSearchResultsRecyclerView: ")

        searchResultsAdapter.addLoadStateListener { combinedLoadStates ->
            onLoadStateChanged(combinedLoadStates)
        }
        binding.searchResultsContainer.setGridLayout(searchResultsAdapter)
    }

    private fun onLoadStateChanged(combinedLoadStates: CombinedLoadStates) {

        if (
            (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.prepend.endOfPaginationReached) ||
            combinedLoadStates.source.refresh is LoadState.Error
        ) {

            // FIRST LOAD COMPLETED(either success or error)

            // binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.isVisible = false
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

    private fun refreshDiscoverTvShows(tvShowId: Int) {
        Log.d(TAG, "getPopularTvShows: ")
        // Make sure we cancel the previous job before creating a new one
        fetchTopRatedTvShowsJob?.cancel()
        fetchTopRatedTvShowsJob = lifecycleScope.launchWhenStarted {
            Log.d(TAG, "getPopularTvShows: launchWhenStarted")
            viewModel.getTvShowsPagingData(tvShowId)
                .collectLatest { tvShowPagingData: PagingData<TvShowData> ->
                    Log.d(TAG, "getPopularTvShows: collectLatest")
                    binding.progressBar.isVisible = false
                    binding.dataLoadErrorView.isVisible = false
                    binding.noDataTextView.isVisible = false
                    searchResultsAdapter.submitData(tvShowPagingData)
                }
        }
    }


    companion object {

        private const val TAG = "TvImagesFragment"
        const val KEY_TV_SHOW_DETAILS = "${AppConstants.PACKAGE_NAME}.key_tv_show_details"

        public fun getInstance(tvShowDetails: TvShowDetails): Fragment {
            val movieStoriesFragment = TvSimilarShowFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_TV_SHOW_DETAILS, tvShowDetails)
            movieStoriesFragment.arguments = bundle
            return movieStoriesFragment
        }

    }

}