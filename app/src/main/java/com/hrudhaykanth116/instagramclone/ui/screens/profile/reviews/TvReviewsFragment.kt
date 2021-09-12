package com.hrudhaykanth116.instagramclone.ui.screens.profile.reviews

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
import com.hrudhaykanth116.instagramclone.data.constants.AppConstants
import com.hrudhaykanth116.instagramclone.data.models.GetTvReviewsResponse
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.databinding.FragmentTvReviewsBinding
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TvReviewsFragment : BaseFragment() {

    lateinit var binding: FragmentTvReviewsBinding
    private val viewModel: TvReviewsViewModel by viewModels()

    private val tvReviewsAdapter by lazy {
        TvReviewsAdapter()
    }

    private var fetchTopRatedTvShowsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvReviewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<TvShowDetails>(KEY_TV_SHOW_DETAILS)?.let { tvShowDetails: TvShowDetails ->
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

        binding.searchResultsContainer.adapter = tvReviewsAdapter

        tvReviewsAdapter.addLoadStateListener { combinedLoadStates ->
            onLoadStateChanged(combinedLoadStates)
        }
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
                    tvReviewsAdapter.itemCount == 0

        binding.noDataTextView.isVisible = isListEmpty


        binding.dataLoadErrorView.isVisible =
            combinedLoadStates.source.refresh is LoadState.Error
                    && tvReviewsAdapter.itemCount < 1

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
                .collectLatest { tvShowPagingData: PagingData<GetTvReviewsResponse.ReviewDetails> ->
                    Log.d(TAG, "getPopularTvShows: collectLatest")
                    binding.progressBar.isVisible = false
                    binding.dataLoadErrorView.isVisible = false
                    binding.noDataTextView.isVisible = false
                    tvReviewsAdapter.submitData(tvShowPagingData)
                }
        }
    }


    companion object{

        private const val TAG = "TvReviewsFragment"
        const val KEY_TV_SHOW_DETAILS = "${AppConstants.PACKAGE_NAME}.key_tv_show_details"

        public fun getInstance(tvShowDetails: TvShowDetails): Fragment {
            val movieStoriesFragment = TvReviewsFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_TV_SHOW_DETAILS, tvShowDetails)
            movieStoriesFragment.arguments = bundle
            return movieStoriesFragment
        }

    }

}