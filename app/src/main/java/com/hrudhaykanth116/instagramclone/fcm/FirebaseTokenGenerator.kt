package com.hrudhaykanth116.instagramclone.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

class FirebaseTokenGenerator {

    companion object {

        private val TAG = FirebaseTokenGenerator::class.java.simpleName

        fun generateToken() {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result?.token
                    Log.d(TAG, "generateToken: token: $token")
                } else {
                    Log.e(TAG, "generateToken: Unable to retrieve token.")
                }
            }
        }

    }

}