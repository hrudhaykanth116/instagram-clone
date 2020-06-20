package com.hrudhaykanth116.instagramclone.notifications

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

object NotificationsManager {

    private val TAG = NotificationsManager.toString()

    private fun showNotification(
        context: Context,
        notification: Notification,
        notificationId: Int
    ) {
        Log.d(TAG, "showNotification: ")
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }

    }

    fun handleRemoteNotification(context: Context, notification: RemoteMessage.Notification) {
        Log.d(TAG, "handleRemoteNotification: ")
        val builder = NotificationsBuilder.buildNotification(context, notification)
        showNotification(context, builder.build(), createUniqueNotificationId())
    }

    private fun createUniqueNotificationId(): Int {
        return SimpleDateFormat("ddHHmmss", Locale.US).format(Date()).toInt()
    }


}