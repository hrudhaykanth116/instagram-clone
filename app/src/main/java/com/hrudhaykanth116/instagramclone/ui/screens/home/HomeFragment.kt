package com.hrudhaykanth116.instagramclone.ui.screens.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.ui.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.databinding.FragmentHomeBinding
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFragmentAdapter: HomeFragmentAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.shimmerFrameLayout.startShimmer()
        initMainPostsRecyclerView()
        initViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        homeViewModel.popularTvShowsLiveData.observe(viewLifecycleOwner,
            Observer<PagedList<TvShowData>> { pagedList ->
                // Data source changed.
                if (pagedList.isNullOrEmpty()) {

                }else{
                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.isGone = true
//                    binding.homePopularTvShowsRV.isVisible = true
                    homeFragmentAdapter.submitList(pagedList)
                }
            }
        )
        homeViewModel.networkState.observe(viewLifecycleOwner,
            Observer { networkState ->
                homeFragmentAdapter.setNetworkState(networkState)
            }
        )

        homeViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            homeFragmentAdapter.updateMovieDataList(it)
        })

    }

    private fun initMainPostsRecyclerView() {
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

        homeViewModel.refreshData()
        Handler().postDelayed(Runnable {
            binding.mainPostsSwipeRefreshLayout.isRefreshing = false
        }, 1000)

    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

}
