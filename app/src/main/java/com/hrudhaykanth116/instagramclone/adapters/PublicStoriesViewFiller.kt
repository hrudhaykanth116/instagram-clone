package com.hrudhaykanth116.instagramclone.adapters

import com.hrudhaykanth116.instagramclone.viewholders.PublicStatusViewHolder

class PublicStoriesViewFiller{

    public fun fillPublicStatusView(publicStatusViewHolder: PublicStatusViewHolder) {
        val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }


        publicStatusViewHolder.publicStoriesContainer.adapter =
            PublicStoryThumbnailAdapter(testList)
    }

}