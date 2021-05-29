package com.hrudhaykanth116.instagramclone.ui.viewholders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.ui.adapters.PublicStoryThumbnailAdapter
import com.hrudhaykanth116.instagramclone.databinding.StoriesViewBinding
import com.hrudhaykanth116.instagramclone.repository.models.MovieData

class PublicStoriesViewHolder(private val binding: StoriesViewBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
//        setIsRecyclable(false)
    }

    public fun bind(movieDataList: ArrayList<MovieData>) {

        /*val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }*/

        // TODO: 27-07-2020 Do not reset the adapter on every bind
        binding.publicStoriesRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.publicStoriesRv.adapter = PublicStoryThumbnailAdapter(movieDataList)

    }

}