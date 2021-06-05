package com.hrudhaykanth116.instagramclone.utils.extensions


fun String.getNonEmptyString(): String? {

    return if (isNullOrBlank()) {
        null
    }else{
        this
    }

}