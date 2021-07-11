package com.hrudhaykanth116.instagramclone.ui.screens.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.ActivityFragmentBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.ActivityFragmentAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.utils.errorutils.NetworkErrorToastManager
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ActivityFragment : BaseFragment() {

    private lateinit var binding: ActivityFragmentBinding
    private val viewModel: ActivityViewModel by viewModels()

    private val activityFragmentAdapter: ActivityFragmentAdapter by lazy {
        ActivityFragmentAdapter(arrayListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            binding.progressBar.visibility = View.VISIBLE
            fetchData()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            reloadData()
        }

        binding.activityRecyclerView.adapter = activityFragmentAdapter
    }

    private fun reloadData() {
        fetchData()
    }

    private fun fetchData() {
        val pageId = Random.nextInt(1, 10)
        viewModel.getAiringTodayShows(pageId)
    }

    private fun initObservers() {
        viewModel.airingTodayShowsLiveData.observe(viewLifecycleOwner) {

            when (it.status) {

                Resource.Status.LOADING -> { }

                Resource.Status.SUCCESS -> {
                    onDataLoaded(it)
                }

                Resource.Status.ERROR -> {
                    binding.progressBar.isGone = true
                    binding.swipeRefreshLayout.isRefreshing = false
                    NetworkErrorToastManager.showToast(requireContext(), it.error)
                }

            }
        }
    }

    private fun onDataLoaded(tvShowDataPagedResponseResource: Resource<TvShowDataPagedResponse>) {
        binding.progressBar.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        val tvShowsList: List<TvShowData>? = tvShowDataPagedResponseResource.data?.tvShowsList
        if (tvShowsList != null) {
            activityFragmentAdapter.updateList(tvShowsList)
        } else {
            ToastHelper.showErrorToast(requireContext(), "No data found.")
        }
    }

}
