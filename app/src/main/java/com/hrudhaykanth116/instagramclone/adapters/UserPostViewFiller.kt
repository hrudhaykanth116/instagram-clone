package com.hrudhaykanth116.instagramclone.adapters

import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.models.UserPost

class UserPostViewFiller {

    public fun fillPostView(
        postViewHolder: MainPostsAdapter.PostViewHolder,
        userPost: UserPost?
    ) {
        val postImgUrl = userPost?.images?.standardResolution?.url
        val postDpUrl = userPost?.user?.profilePictureUrl

        postViewHolder.postUserNameTV.text = userPost?.user?.username


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