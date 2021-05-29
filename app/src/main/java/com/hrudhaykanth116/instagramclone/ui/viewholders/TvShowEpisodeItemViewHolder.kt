package com.hrudhaykanth116.instagramclone.ui.viewholders

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.ui.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.TvShowEpisodeItemBinding
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData

class TvShowEpisodeItemViewHolder(private val binding: TvShowEpisodeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    public fun bind(
        tvShowData: TvShowData,
        postClickListener: HomeFragmentAdapter.IPostClickListener
    ) {
        val postImgUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.backdropPath
        val postDpUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
        val context = itemView.context

        binding.postUserNameTV.text = tvShowData.name
        binding.postUserNameTV.setOnClickListener {
            postClickListener.onProfileNameClicked(tvShowData)
        }
        // Tv show not liked case
        binding.likeIcon.clearColorFilter()

        binding.likedDescriptionTV.text = context.getString(R.string.likes, tvShowData.voteCount.toString())
        binding.showNameTV.text = tvShowData.originalName

        binding.showOverView.text = tvShowData.overview
        // Add proper logic to set number of lines appropriately.
        binding.showOverView.post {
            // Update view, after the view is rendered.
            if (binding.showOverView.lineCount > 3) {
                binding.showOverView.setLines(3)
            }
        }

        var lastClickTime: Long = 0
        binding.tvShowImageView.setOnClickListener {
            if(System.currentTimeMillis() - lastClickTime < 300){
                // Double clicked

                binding.likeIcon.setColorFilter(Color.rgb(255, 0, 0))

                binding.heartImageView.visibility = View.VISIBLE
                Handler().postDelayed({
                    // TODO: 26-07-2020 perform bounce visible animation 
                    binding.heartImageView.visibility = View.GONE
                }, 1000)

                lastClickTime = 0
            }else{
                lastClickTime = System.currentTimeMillis()
            }
        }


        loadImg(context, postImgUrl, binding.tvShowImageView)
        loadImg(context, postDpUrl, binding.dpView.innerImg)

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