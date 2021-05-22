package com.hrudhaykanth116.instagramclone.viewholders

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.adapters.PublicStoryThumbnailAdapter
import com.hrudhaykanth116.instagramclone.databinding.StoriesViewBinding
import com.hrudhaykanth116.instagramclone.models.MovieData

class PublicStoriesViewHolder(binding: StoriesViewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val publicStoriesContainer = binding.publicStoriesRv

    public fun bind(movieDataList: ArrayList<MovieData>) {

        /*val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }*/

        // TODO: 27-07-2020 Do not reset the adapter on every bind
        publicStoriesContainer.adapter = PublicStoryThumbnailAdapter(movieDataList)

    }

}