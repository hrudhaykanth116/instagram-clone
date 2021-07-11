package com.hrudhaykanth116.instagramclone.utils.extensions.livedata

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}