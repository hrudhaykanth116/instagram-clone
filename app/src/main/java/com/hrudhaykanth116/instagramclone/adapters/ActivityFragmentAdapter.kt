package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.profile_suggestion_layout.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*

class ActivityFragmentAdapter(private val tvShowDataList: List<TvShowData>):
    RecyclerView.Adapter<ActivityFragmentAdapter.ActivityShowViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityShowViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.profile_suggestion_layout, parent, false)
        return ActivityShowViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tvShowDataList.size
    }

    override fun onBindViewHolder(activityShowViewHolder: ActivityShowViewHolder, position: Int) {
        val tvShowData = tvShowDataList[position]
        activityShowViewHolder.bind(tvShowData)
    }


    class ActivityShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val showName: TextView = itemView.showName as TextView
        private val showRatings: TextView = itemView.showRating as TextView
        private val showImageView: ImageView = itemView.showImage.innerImg as ImageView

        public fun bind(tvShowData: TvShowData) {

            showName.text = tvShowData.name
            showRatings.text = tvShowData.voteAverage.toString()
            val showImgUrl: String = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
            Glide.with(itemView.context).load(showImgUrl).into(showImageView)

        }

    }

}