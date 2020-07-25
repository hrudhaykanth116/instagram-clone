package com.hrudhaykanth116.instagramclone.viewholders

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.tv_show_episode_item.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*

class TvShowEpisodeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Can use view directly instead of this private field
    private val userDpView: ImageView = itemView.dpView.innerImg as ImageView
    private val tvShowImageView: ImageView = itemView.tvShowImageView as ImageView
    private val heartImageView: ImageView = itemView.heartImageView as ImageView
    private val likedIcon: ImageView = itemView.likeIcon as ImageView
    private val userNameTV: TextView = itemView.postUserNameTV as TextView
    private val likedDescriptionTV: TextView = itemView.likedDescriptionTV as TextView
    private val showNameTV: TextView = itemView.showNameTV as TextView
    private val showOverViewTV: TextView = itemView.showOverView as TextView

    public fun bind(
        tvShowData: TvShowData,
        postClickListener: HomeFragmentAdapter.IPostClickListener
    ) {
        val postImgUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.backdropPath
        val postDpUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
        val context = itemView.context

        userNameTV.text = tvShowData.originalName
        userNameTV.setOnClickListener {
            postClickListener.onProfileNameClicked(tvShowData)
        }
        // Tv show not liked case
        likedIcon.clearColorFilter()

        likedDescriptionTV.text = context.getString(R.string.likes, tvShowData.voteCount.toString())
        showNameTV.text = tvShowData.originalName

        showOverViewTV.text = tvShowData.overview
        // Add proper logic to set number of lines appropriately.
        showOverViewTV.post {
            // Update view, after the view is rendered.
            if (showOverViewTV.lineCount > 3) {
                showOverViewTV.setLines(3)
            }
        }

        var lastClickTime: Long = 0
        tvShowImageView.setOnClickListener {
            if(System.currentTimeMillis() - lastClickTime < 300){
                // Double clicked

                likedIcon.setColorFilter(Color.rgb(255, 0, 0))

                heartImageView.visibility = View.VISIBLE
                Handler().postDelayed({
                    // TODO: 26-07-2020 perform bounce visible animation 
                    heartImageView.visibility = View.GONE
                }, 1000)

                lastClickTime = 0
            }else{
                lastClickTime = System.currentTimeMillis()
            }
        }


        loadImg(context, postImgUrl, tvShowImageView)
        loadImg(context, postDpUrl, userDpView)

    }

    private fun loadImg(
        context: Context,
        postImgUrl: String,
        imageView: ImageView
    ) {
        Glide
            .with(context)
            .load(postImgUrl)
            .into(imageView)
    }

}