package com.hrudhaykanth116.instagramclone.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrudhaykanth116.instagramclone.models.UserPostsData

class HomeViewModel : ViewModel() {

    private val postsList: ArrayList<UserPostsData> = ArrayList()
    val postsLiveData = MutableLiveData<List<UserPostsData>>()

    public fun updateList(postsList: ArrayList<UserPostsData>){
        postsList.clear()
        postsList.addAll(postsList)
        postsLiveData.value = postsList
    }

    public fun addNewList(newPostsList: ArrayList<UserPostsData>){
        postsList.addAll(0, newPostsList)
        postsLiveData.value = postsList
    }

}