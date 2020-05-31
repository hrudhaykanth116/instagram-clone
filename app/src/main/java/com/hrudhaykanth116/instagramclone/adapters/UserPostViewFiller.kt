package com.hrudhaykanth116.instagramclone.adapters

import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.models.UserPostsData

class UserPostViewFiller {

    public fun fillPostView(
        postViewHolder: MainPostsAdapter.PostViewHolder,
        userPost: UserPostsData.UserPost?
    ) {
        val postImgUrl = userPost?.images?.imageDetails?.url
        val postDpUrl = userPost?.user?.profilePictureUrl

        val context = postViewHolder.itemView.context
        Glide
            .with(context)
            .load(postImgUrl)
            .into(postViewHolder.postImgView)

        Glide
            .with(context)
            .load(postDpUrl)
            .into(postViewHolder.postUserDpView)
    }

}