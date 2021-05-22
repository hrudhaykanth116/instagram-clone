package com.hrudhaykanth116.instagramclone.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class FirebaseTokenGenerator {

    fun generateToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d(TAG, "generateToken: token: $token")
            } else {
                Log.e(TAG, "generateToken: Unable to retrieve token.")
            }
        }
    }

    companion object {
        private val TAG = FirebaseTokenGenerator::class.java.simpleName
    }

}