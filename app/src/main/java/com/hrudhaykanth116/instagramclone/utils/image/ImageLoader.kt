package com.hrudhaykanth116.instagramclone.utils.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object ImageLoader {

    fun load(
        imgUrl: String,
        targetImageView: ImageView,
        context: Context? = null,
        errorDrawable: Int? = null
    ){
        Glide
            .with(context ?: targetImageView.context)
            .load(imgUrl)
            .thumbnail(0.05f).transition(
                DrawableTransitionOptions.withCrossFade()
            ).error(errorDrawable)
            .into(targetImageView)
    }

    fun load(
        context: Context,
        imgUrl: String,
        thumbNailImgUrl: String,
        targetImageView: ImageView
    ){
        Glide
            .with(context)
            .load(imgUrl)
            .thumbnail(
                Glide.with(context).load(thumbNailImgUrl)
            )
            .into(targetImageView)
    }

}