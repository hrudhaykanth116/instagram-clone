package com.hrudhaykanth116.instagramclone.adapters

import com.hrudhaykanth116.instagramclone.models.MovieData
import com.hrudhaykanth116.instagramclone.viewholders.PublicStoriesViewHolder

class PublicStoriesViewFiller{

    public fun fillView(publicStoryViewHolder: PublicStoriesViewHolder, movieDataList: List<MovieData>) {
        /*val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }*/

        publicStoryViewHolder.bind(movieDataList)
    }

}