package com.hrudhaykanth116.instagramclone.adapters

import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.models.TvShowData

class UserPostViewFiller {

    public fun fillPostView(
        postViewHolder: MainPostsAdapter.PostViewHolder,
        tvShowData: TvShowData
    ) {
        val postImgUrl = "http://image.tmdb.org/t/p/original/${tvShowData.backdropPath}"
        val postDpUrl = "http://image.tmdb.org/t/p/original/${tvShowData.posterPath}"

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

        Glide
            .with(context)
            .load(postImgUrl).placeholder(R.drawable.images_placeholder)
            .into(postViewHolder.contentTV)

        Glide
            .with(context)
            .load(postDpUrl)
            .into(postViewHolder.userDpView)
    }

}