package com.hrudhaykanth116.instagramclone.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrudhaykanth116.instagramclone.models.UserPost

class HomeViewModel : ViewModel() {

    private val postsList: ArrayList<UserPost> = ArrayList()
    val postsLiveData = MutableLiveData<List<UserPost>>()

    public fun updateList(postsList: ArrayList<UserPost>){
        postsList.clear()
        postsList.addAll(postsList)
        postsLiveData.value = postsList
    }

    public fun addNewList(newPostsList: ArrayList<UserPost>){
        postsList.addAll(0, newPostsList)
        postsLiveData.value = postsList
    }

}