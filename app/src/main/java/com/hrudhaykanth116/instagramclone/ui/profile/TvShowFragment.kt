package com.hrudhaykanth116.instagramclone.ui.profile

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.MainActivity
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.TvShowImagesAdapter
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.TvShowFragmentBinding
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.network.RetroApis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowFragment : Fragment() {

    lateinit var binding: TvShowFragmentBinding
    private lateinit var apisClient: RetroApis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TvShowFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.apisClient = (activity as MainActivity).apisClient

        initViews()

    }

    private fun initViews() {
        binding.tvShowBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val tvShowData = arguments?.let {
            TvShowFragmentArgs.fromBundle(it).tvShowData
        }

        tvShowData?.let {
            binding.tvShowName.text = it.name ?: "Tv show name"

            val tvShowImageView = binding.tvShowImage.innerImg
            Glide
                .with(tvShowImageView)
                .load(MoviesDbConstants.IMAGES_BASE_URL + it.posterPath)
                .into(tvShowImageView)

            getTvShowDetailsAndFillView(tvShowData)
        }


    }

    private fun getTvShowDetailsAndFillView(tvShowData: TvShowData) {
        tvShowData.id?.let {
            apisClient.getTvShowDetails(it).enqueue(object : Callback<TvShowDetails> {
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