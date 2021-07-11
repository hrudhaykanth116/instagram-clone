package com.hrudhaykanth116.instagramclone.utils.extensions.livedata

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<ArrayList<T>?>.add(item: T) {
    val updatedItems = this.value ?: arrayListOf()
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<ArrayList<T>?>.addAll(items: ArrayList<T>) {
    val updatedItems = this.value ?: arrayListOf()
    updatedItems.addAll(items)
    this.value = updatedItems
}

fun <T> MutableLiveData<ArrayList<T>?>.clear() {
    val updatedItems = this.value
    if (updatedItems != null) {
        updatedItems.clear()
        this.value = updatedItems
    }
}

fun <T> MutableLiveData<ArrayList<T>?>.contains(item: T): Boolean {
    val updatedItems = this.value
    return updatedItems?.contains(item) == true
}

fun <T> MutableLiveData<ArrayList<T>?>.remove(item: T) {
    val updatedItems = this.value
    val isRemoved = updatedItems?.remove(item)
    if (isRemoved == true) {
        this.value = updatedItems
    }
}

fun <T> Set<T>?.contentEquals(list :Set<T>?): Boolean {
    return when {
        this == null -> {
            list == null
        }
        list == null -> {
            false
        }
        this.size != list.size -> {
            false
        }
        else -> {
            this.containsAll(list) && list.containsAll(this)
        }
    }
}