package com.hrudhaykanth116.instagramclone.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.FragmentTvShowDetailsBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.TvShowImagesAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.utils.extensions.getNonEmptyString
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentTvShowDetailsBinding
    private val viewmodel: TvShowDetailsViewModel by viewModels()
    private val tvShowDetailsFragmentArgs: TvShowDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            loadData(tvShowDetailsFragmentArgs.tvShowData)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewmodel.tvShowDetailsLiveData.observe(viewLifecycleOwner) { tvShowDetailsResource: Resource<TvShowDetails> ->
            when (tvShowDetailsResource.status) {
                Resource.Status.SUCCESS -> {
                    if (tvShowDetailsResource.data != null) {
                        fillView(tvShowDetailsResource.data)
                    } else {
                        handleError(message = "No data available")
                    }
                }
                Resource.Status.ERROR -> {
                    handleError(tvShowDetailsResource.error)
                }
                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun handleError(error: Resource.Error? = null, message: String? = null) {
        ToastHelper.showErrorToast(
            requireContext(),
            error?.message ?: message
        )
    }

    private fun initViews() {

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val tvShowData = arguments?.let {
            TvShowDetailsFragmentArgs.fromBundle(it).tvShowData
        }

        tvShowData?.let {
            binding.topAppBar.title =
                it.name?.getNonEmptyString() ?: getString(R.string.tv_show_name_unavailable)

            val tvShowImageView = binding.tvShowImage.innerImg
            ImageLoader.load(MoviesDbConstants.IMAGES_BASE_URL + it.posterPath, tvShowImageView)

        }


    }

    private fun loadData(tvShowData: TvShowData) {
        if (tvShowData.id == null) {
            handleError(message = "No id for the Tv show")
        } else {
            viewmodel.getTvShowDetails(tvShowData.id)
        }
    }

    private fun fillView(tvShowDetails: TvShowDetails) {

        // TODO: 07/06/21 Use data binding.

        binding.showRatingTV.text = tvShowDetails.voteAverage.toString()
        binding.showSeasonsCountTV.text = tvShowDetails.numberOfSeasons.toString()
        binding.showEpisodesCountTv.text = tvShowDetails.numberOfEpisodes.toString()
        if (tvShowDetails.homepage.isNullOrEmpty()) {
            binding.tvShowWebPage.visibility = View.GONE
        } else {
            binding.tvShowWebPage.text = tvShowDetails.homepage
        }
        binding.tvShowOverview.text = tvShowDetails.overview


        val seasonPosterPathList = ArrayList<String>()
        tvShowDetails.seasons?.forEach { seasonDetail ->
            seasonDetail?.posterPath?.let {
                seasonPosterPathList.add(MoviesDbConstants.IMAGES_BASE_URL + it)
            }
        }

        binding.tvShowImages.adapter = TvShowImagesAdapter(seasonPosterPathList)
        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.tvShowImages.layoutManager = gridLayoutManager
    }

}