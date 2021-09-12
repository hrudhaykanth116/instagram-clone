package com.hrudhaykanth116.instagramclone.ui.screens.profile.castcrew

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.constants.AppConstants
import com.hrudhaykanth116.instagramclone.data.models.GetTvCreditsResponse
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.FragmentShowImagesBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.TvShowImagesAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvCastFragment : BaseFragment() {

    private val imagesAdapter by lazy {
        TvShowImagesAdapter()
    }

    lateinit var binding: FragmentShowImagesBinding
    private val viewModel: TvCastViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowImagesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<TvShowDetails>(KEY_TV_SHOW_DETAILS)?.let { tvShowDetails: TvShowDetails ->
                viewModel.setTvId(tvShowDetails.id!!)
            }
        }

        initViews()
        initObservers()

    }

    private fun initObservers() {

        viewModel.tvCreditsResponseLiveData.observe(viewLifecycleOwner){ resource: Resource<GetTvCreditsResponse> ->
            when (resource.status) {

                Resource.Status.LOADING -> { }

                Resource.Status.SUCCESS -> {
                    onCreditsDataLoaded(resource.data)
                }

                Resource.Status.ERROR -> {
                }

            }
        }

    }

    private fun onCreditsDataLoaded(data: GetTvCreditsResponse?) {

        binding.progressBar.isVisible = false
        binding.dataLoadErrorView.isVisible = false
        binding.noDataTextView.isVisible = false

        val cast: List<GetTvCreditsResponse.Cast?> = data?.cast ?: emptyList()

        val castProfileUrl: List<String> = cast.map {
            MoviesDbConstants.IMAGES_BASE_URL + it?.profile_path
        }

        imagesAdapter.updateList(castProfileUrl)

    }

    private fun initViews() {
        initSearchResultsRecyclerView()
    }

    private fun initSearchResultsRecyclerView() {
        Log.d(TAG, "initSearchResultsRecyclerView: ")

        val staggeredGridLayoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.searchResultsContainer.adapter = imagesAdapter
        binding.searchResultsContainer.layoutManager = staggeredGridLayoutManager

    }

    companion object{

        private const val TAG = "TvCastFragment"
        const val KEY_TV_SHOW_DETAILS = "${AppConstants.PACKAGE_NAME}.key_tv_show_details"

        public fun getInstance(tvShowDetails: TvShowDetails): Fragment {
            val movieStoriesFragment = TvCastFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_TV_SHOW_DETAILS, tvShowDetails)
            movieStoriesFragment.arguments = bundle
            return movieStoriesFragment
        }

    }

}