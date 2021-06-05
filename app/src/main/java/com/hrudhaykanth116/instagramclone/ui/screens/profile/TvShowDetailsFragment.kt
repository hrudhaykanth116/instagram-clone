package com.hrudhaykanth116.instagramclone.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.FragmentTvShowDetailsBinding
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.ui.adapters.TvShowImagesAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.utils.extensions.getNonEmptyString
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class TvShowDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentTvShowDetailsBinding

    @Inject
    lateinit var retroApis: RetroApis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initViews()

    }

    private fun initViews() {



        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val tvShowData = arguments?.let {
            TvShowDetailsFragmentArgs.fromBundle(it).tvShowData
        }

        tvShowData?.let {
            binding.topAppBar.title = it.name?.getNonEmptyString() ?: getString(R.string.tv_show_name_unavailable)

            val tvShowImageView = binding.tvShowImage.innerImg
            ImageLoader.load(MoviesDbConstants.IMAGES_BASE_URL + it.posterPath, tvShowImageView)

            getTvShowDetailsAndFillView(tvShowData)
        }


    }

    private fun getTvShowDetailsAndFillView(tvShowData: TvShowData) {
        tvShowData.id?.let {
            retroApis.getTvShowDetails(it).enqueue(object : Callback<TvShowDetails> {
                override fun onFailure(call: Call<TvShowDetails>, t: Throwable) {
                    Toast.makeText(context, "Unable to fetch tv show details", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<TvShowDetails>,
                    response: Response<TvShowDetails>
                ) {

                    val tvShowDetails: TvShowDetails? = response.body()
                    tvShowDetails?.let {
                        fillView(tvShowDetails)
                    }

                }

            })
        }
    }

    private fun fillView(tvShowDetails: TvShowDetails) {

        binding.showRatingTV.text = tvShowDetails.voteAverage.toString()
        binding.showSeasonsCountTV.text = tvShowDetails.numberOfSeasons.toString()
        binding.showEpisodesCountTv.text = tvShowDetails.numberOfEpisodes.toString()
        if (tvShowDetails.homepage.isNullOrEmpty()) {
            binding.tvShowWebPage.visibility = View.GONE
        }else{
            binding.tvShowWebPage.text = tvShowDetails.homepage
        }
        binding.tvShowOverview.text = tvShowDetails.overview


        val seasonPosterPathList = ArrayList<String>()
        tvShowDetails.seasons?.forEach { seasonDetail ->
            if (seasonDetail != null) {
                seasonDetail.posterPath?.let { seasonPosterPathList.add(MoviesDbConstants.IMAGES_BASE_URL + it) }
            }
        }

        // Dummy list size
        /*for (i: Int in 1..100){
            seasonPosterPathList.add("dummy")
        }*/


        binding.tvShowImages.adapter = TvShowImagesAdapter(seasonPosterPathList)
        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.tvShowImages.layoutManager = gridLayoutManager
    }

}