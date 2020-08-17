package com.hrudhaykanth116.instagramclone.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.adapters.PublicStoryThumbnailAdapter
import com.hrudhaykanth116.instagramclone.models.MovieData
import kotlinx.android.synthetic.main.stories_view.view.*

class PublicStoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val publicStoriesContainer = itemView.public_stories_rv

    public fun bind(movieDataList: ArrayList<MovieData>) {

        /*val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }*/

        // TODO: 27-07-2020 Do not reset the adapter on every bind
        publicStoriesContainer.adapter = PublicStoryThumbnailAdapter(movieDataList)

    }

}