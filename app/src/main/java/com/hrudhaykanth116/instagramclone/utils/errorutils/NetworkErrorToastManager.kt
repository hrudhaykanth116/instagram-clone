package com.hrudhaykanth116.instagramclone.utils.errorutils

import android.content.Context
import android.util.Log
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper

object NetworkErrorToastManager {

    private const val TAG = "NetworkErrorHandler"

    fun showToast(context: Context, error: Resource.Error?){
        Log.d(TAG, "showToast: ", )
        if (error == null) {
            ToastHelper.showErrorToast(context, msg = "Not responding.")
        } else {
            when (error.code) {
                Resource.ErrorCode.NO_INTERNET ->
                    ToastHelper.showErrorToast(context, "No internet connection.")
                else -> {
                    ToastHelper.showErrorToast(context, msg = error.message.toString())
                }
            }
        }
    }

}