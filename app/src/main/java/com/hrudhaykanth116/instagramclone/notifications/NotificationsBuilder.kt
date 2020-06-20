package com.hrudhaykanth116.instagramclone.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.hrudhaykanth116.instagramclone.R

object NotificationsBuilder {

    fun buildNotification(
        context: Context,
        notification: RemoteMessage.Notification
    ): NotificationCompat.Builder {
        return NotificationCompat
            .Builder(context, NotificationChannelsInfo.DEFAULT_CHANNEL_ID)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setSmallIcon(R.drawable.ic_mood_white_24dp)
            .setPriority(NotificationCompat.PRIORITY_LOW)
    }

}