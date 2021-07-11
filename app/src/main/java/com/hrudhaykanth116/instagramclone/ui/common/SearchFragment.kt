package com.hrudhaykanth116.instagramclone.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.FragmentSearchBinding
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val viewModel: SearchViewModel by viewModels()

    val searchListAdapter: SearchListAdapter by lazy {
        SearchListAdapter { tvShowData: TvShowData ->
            // Navigate to the item.

            tvShowData.id?.let {
                val action: NavDirections =
                    SearchFragmentDirections.actionSearchFragmentToUserProfileNavScreen(
                        it
                    )
                findNavController().navigate(action)
            } ?: run{
                ToastHelper.showErrorToast(requireContext(), "No tv id.")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObservers() {
        viewModel.tvShowSearchList.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.searchResultsListRV.isVisible = false
                    binding.searchProgressBar.isVisible = true
                    binding.searchStatus.isVisible = true
                    binding.searchStatus.text = "Searching for: ${viewModel.searchQuery}"
                }
                Resource.Status.SUCCESS -> {
                    val searchResults: List<TvShowData?>? = it.data?.tvShowDataList
                    // TODO: 05/07/21 Improve the logic or use normal adapters.
                    searchListAdapter.submitList(searchResults ?: arrayListOf())
                    if (searchResults.isNullOrEmpty()) {
                        binding.searchResultsListRV.isVisible = false
                        binding.searchStatus.isVisible = true
                        binding.searchStatus.text = if (viewModel.searchQuery.isBlank())
                            "Start typing tv show name to search for it."
                        else
                            "No results found"
                    } else {
                        binding.searchResultsListRV.isVisible = true
                        binding.searchStatus.isVisible = false
                    }
                    binding.searchProgressBar.isVisible = false
                }
                Resource.Status.ERROR -> {
                    binding.searchResultsListRV.isVisible = false
                    binding.searchProgressBar.isVisible = false
                    binding.searchStatus.isVisible = true
                    binding.searchStatus.text = it.error?.message
                }
            }
        }
    }

    private fun initViews() {

        binding.searchResultsListRV.isVisible = false
        binding.searchProgressBar.isVisible = false
        binding.searchStatus.isVisible = true
        binding.searchStatus.text = if (viewModel.searchQuery.isBlank())
            "Start typing tv show name to search for it."
        else
            "No results found"

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchInputView.doOnTextChanged { text, _, _, _ ->
            viewModel.searchQuery = text.toString()
        }
        initSearchResultsRecyclerView()
    }

    private fun initSearchResultsRecyclerView() {
        binding.searchResultsListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultsListRV.adapter = searchListAdapter
    }
}