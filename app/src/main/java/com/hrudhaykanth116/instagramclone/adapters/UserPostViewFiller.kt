package com.hrudhaykanth116.instagramclone.adapters

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.viewholders.PostViewHolder

class UserPostViewFiller {

    public fun fillPostView(
        postViewHolder: PostViewHolder,
        tvShowData: TvShowData
    ) {
        val postImgUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.backdropPath
        val postDpUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
        val context = postViewHolder.itemView.context

        postViewHolder.userNameTV.text = tvShowData.originalName
        postViewHolder.likedDescriptionTV.text = context.getString(R.string.likes, tvShowData.voteCount.toString())
        val captionTV = postViewHolder.captionTV
        captionTV.text = context.getString(R.string.post_caption, tvShowData.originalName, tvShowData.overview)
        captionTV.post {
            if (captionTV.lineCount > 2){
                captionTV.setLines(2)
            }
        }

        loadImg(context, postImgUrl, postViewHolder.contentTV)
        loadImg(context, postDpUrl, postViewHolder.userDpView)

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