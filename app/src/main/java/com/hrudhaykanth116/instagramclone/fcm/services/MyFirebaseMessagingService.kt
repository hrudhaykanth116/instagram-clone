package com.hrudhaykanth116.instagramclone.fcm.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hrudhaykanth116.instagramclone.notifications.NotificationsManager


class MyFirebaseMessagingService : FirebaseMessagingService() {


    /*[google.delivered_priority=high]
    [google.sent_time=1597862327233]
    [google.ttl=2419200]
    [google.original_priority=high]
    [Last date=30-08-2020]
    [id=116]
    [Name=Hrudhay]
    [from=1029922099872]
    [google.message_id=0:1597862327252756%0202bacd0202bacd]
    [collapse_key=com.hrudhaykanth116.smartschoolmanger]*/

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: ")
        if(remoteMessage.data.isNotEmpty()){
            Log.d(TAG, "onMessageReceived: data payload is not empty")
            // Handle data payload if implemented. this has custom key value pairs.
        }

        val notification = remoteMessage.notification

        notification?.let {
            NotificationsManager.handleRemoteNotification(this, notification)
        }


    }

    /**
     * In some situations, FCM may not deliver a message. This occurs when there are too many messages
     * (>100) pending for your app on a particular device at the time it connects or if the device hasn't
     * connected to FCM in more than one month. In these cases, you may receive a callback to
     * FirebaseMessagingService.onDeletedMessages()
     */
    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(token)

    }

    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }

}