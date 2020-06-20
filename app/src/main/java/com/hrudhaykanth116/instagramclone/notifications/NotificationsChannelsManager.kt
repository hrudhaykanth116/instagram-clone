package com.hrudhaykanth116.instagramclone.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationsChannelsManager {

    public fun createDefaultNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                NotificationChannelsInfo.DEFAULT_CHANNEL_ID,
                NotificationChannelsInfo.DEFAULT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.apply {
                description = notificationChannel.description
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

}