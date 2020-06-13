package com.hrudhaykanth116.instagramclone.adapters

import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.models.MovieData

class UserPostViewFiller {

    public fun fillPostView(
        postViewHolder: MainPostsAdapter.PostViewHolder,
        movieData: MovieData
    ) {
        val postImgUrl = "http://image.tmdb.org/t/p/original/${movieData.backdropPath}"
        val postDpUrl = "http://image.tmdb.org/t/p/original/${movieData.posterPath}"

        val context = postViewHolder.itemView.context

        postViewHolder.userNameTV.text = movieData.originalTitle
        postViewHolder.likedDescriptionTV.text = context.getString(R.string.likes, movieData.voteCount.toString())
        val captionTV = postViewHolder.captionTV
        captionTV.text = context.getString(R.string.post_caption, movieData.originalTitle, movieData.overview)
        captionTV.post {
            if (captionTV.lineCount > 2){
                captionTV.setLines(2)
            }
        }


        // TODO: 06-06-2020 Add progress bar
        Glide
            .with(context)
//            .addDefaultRequestListener(object: RequestListener<Any?> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Any>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//            })
            .load(postImgUrl).placeholder(R.drawable.images_placeholder)
            .into(postViewHolder.contentTV)

        Glide
            .with(context)
            .load(postDpUrl)
            .into(postViewHolder.userDpView)
    }

}