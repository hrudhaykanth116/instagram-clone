package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.ProfileSuggestionLayoutBinding
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader

class ActivityFragmentAdapter(private val tvShowDataList: List<TvShowData>):
    RecyclerView.Adapter<ActivityFragmentAdapter.ActivityShowViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityShowViewHolder {
        val binding = ProfileSuggestionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvShowDataList.size
    }

    override fun onBindViewHolder(activityShowViewHolder: ActivityShowViewHolder, position: Int) {
        val tvShowData = tvShowDataList[position]
        activityShowViewHolder.bind(tvShowData)
    }


    class ActivityShowViewHolder(binding: ProfileSuggestionLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        private val showName: TextView = binding.showName
        private val showRatings: TextView = binding.showRating
        private val showImageView: ImageView = binding.showImage.innerImg

        public fun bind(tvShowData: TvShowData) {

            showName.text = tvShowData.name
            showRatings.text = tvShowData.voteAverage.toString()
            val showImgUrl: String = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
            ImageLoader.load(showImgUrl, showImageView)

        }

    }

}