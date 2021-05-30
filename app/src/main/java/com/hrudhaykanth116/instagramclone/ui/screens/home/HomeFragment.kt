package com.hrudhaykanth116.instagramclone.ui.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.ui.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.databinding.FragmentHomeBinding
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFragmentAdapter: HomeFragmentAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private var popularTvShowsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        binding.shimmerFrameLayout.startShimmer()
        initMainPostsRecyclerView()
        initObservers()
        getPopularTvShows()
        homeViewModel.fetchMoviesList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObservers() {
        Log.d(TAG, "initObservers: ")

        homeViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            homeFragmentAdapter.updateMovieDataList(it)
        })

    }

    private fun initMainPostsRecyclerView() {
        Log.d(TAG, "initMainPostsRecyclerView: ")
        homeFragmentAdapter = HomeFragmentAdapter(object : HomeFragmentAdapter.IPostClickListener {
            override fun onProfileNameClicked(tvShowData: TvShowData) {
                val tvShowFragmentAction = HomeFragmentDirections.actionTvShowFragment(tvShowData)
                findNavController().navigate(tvShowFragmentAction)
            }
        })
//        binding.homePopularTvShowsRV.recycledViewPool.setMaxRecycledViews(HomeFragmentAdapter.TYPE_PUBLIC_STORIES, 1)
        binding.homePopularTvShowsRV.layoutManager = LinearLayoutManager(context)
        binding.homePopularTvShowsRV.adapter = homeFragmentAdapter

        binding.mainPostsSwipeRefreshLayout.setOnRefreshListener {
            refreshPosts()
        }

    }

    private fun refreshPosts() {
        Log.d(TAG, "refreshPosts: ")
        homeViewModel.fetchMoviesList()
        getPopularTvShows()
    }

    private fun getPopularTvShows(){
        Log.d(TAG, "getPopularTvShows: ")
        // Make sure we cancel the previous job before creating a new one
        popularTvShowsJob?.cancel()
        popularTvShowsJob = lifecycleScope.launchWhenStarted {
            Log.d(TAG, "getPopularTvShows: launchWhenStarted")
            homeViewModel.getPopularTvShows().collectLatest {
                Log.d(TAG, "getPopularTvShows: collectLatest")
                binding.mainPostsSwipeRefreshLayout.isRefreshing = false
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.isGone = true
                homeFragmentAdapter.submitData(it)
            }
        }
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

}
