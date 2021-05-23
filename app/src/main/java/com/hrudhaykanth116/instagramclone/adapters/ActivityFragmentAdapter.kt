package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.ProfileSuggestionLayoutBinding
import com.hrudhaykanth116.instagramclone.models.TvShowData

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
            Glide.with(itemView.context).load(showImgUrl).into(showImageView)

        }

    }

}