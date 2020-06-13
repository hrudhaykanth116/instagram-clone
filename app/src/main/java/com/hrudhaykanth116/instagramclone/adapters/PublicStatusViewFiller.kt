package com.hrudhaykanth116.instagramclone.adapters

class PublicStatusViewFiller{

    public fun fillPublicStatusView(publicStatusViewHolder: MainPostsAdapter.PublicStatusViewHolder) {
        val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }
        publicStatusViewHolder.publicStoriesContainer.adapter =
            PublicStoryThumbnailAdapter(testList)
    }

}