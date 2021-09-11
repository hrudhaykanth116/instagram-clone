package com.hrudhaykanth116.instagramclone.ui.screens.profile.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hrudhaykanth116.instagramclone.data.constants.AppConstants
import com.hrudhaykanth116.instagramclone.data.models.GetTvVideosResponse
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.FragmentVideosBinding
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvVideosFragment : BaseFragment() {

    lateinit var binding: FragmentVideosBinding
    private val viewModel: TvVideosViewModel by viewModels()
    private val videosAdapter by lazy {
        TvVideosAdapter(lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideosBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            it.getParcelable<TvShowDetails>(KEY_TV_SHOW_DETAILS)?.let { tvShowDetails: TvShowDetails ->
                viewModel.setTvId(tvShowDetails.id!!)
            }
        }
        // val tvShowDetails: TvShowDetails? = arguments?.getParcelable<TvShowDetails>(
        //     KEY_TV_SHOW_DETAILS
        // )
        // if (tvShowDetails != null) {
        //     viewModel.setTvId(tvShowDetails.id!!)
        // }

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.tvVideosContainer.adapter = videosAdapter
    }

    private fun initObservers() {

        viewModel.tvVideos.observe(viewLifecycleOwner){ resource: Resource<GetTvVideosResponse> ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    val results: List<GetTvVideosResponse.Result?>? = resource.data?.results
                    if (results != null) {
                        onVideoLinksAvailable(results)
                    }else{
                        // TODO: 11/09/21 handle null case
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }

        }

    }

    private fun onVideoLinksAvailable(results: List<GetTvVideosResponse.Result?>) {
        val key: String? = results.getOrNull(0)?.key // video id

        val youTubeKeys: List<GetTvVideosResponse.Result?> = results.filter {
            it?.site == "YouTube"
        }

        if (!youTubeKeys.isNullOrEmpty()) {
            binding.noDataView.isVisible = false
            videosAdapter.updateVideoIds(youTubeKeys)
        }else{
            binding.noDataView.isVisible = true
        }
    }

    companion object{

        const val KEY_TV_SHOW_DETAILS = "${AppConstants.PACKAGE_NAME}.key_tv_show_details"

        public fun getInstance(tvShowDetails: TvShowDetails): Fragment {
            val movieStoriesFragment = TvVideosFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_TV_SHOW_DETAILS, tvShowDetails)
            movieStoriesFragment.arguments = bundle
            return movieStoriesFragment
        }

    }

}