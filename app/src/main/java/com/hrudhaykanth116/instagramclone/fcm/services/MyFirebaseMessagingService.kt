package com.hrudhaykanth116.instagramclone.fcm.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: $remoteMessage")
    }

    /*private fun sendNotification(messageBody: String) {
        val intent = Intent(this, PostLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 *//* Request code *//*, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = Builder(this)
            .setSmallIcon(R.drawable.tf2spyprofile)
            .setContentTitle("FCM Message")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService<Any>(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build())
    }*/

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