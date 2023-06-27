package com.aura.bootcounter.uc

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aura.bootcounter.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationDisplayerUseCase @Inject constructor(
    @ApplicationContext
    private val ctx: Context,
    private val notificationManager: NotificationManagerCompat,
) {
    companion object {
        const val CHANNEL_ID = "boot"
        const val NOTIF_ID = 0x100
    }

    fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = ctx.resources.getString(R.string.channel_name)
            val descriptionText = ctx.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun display(
        text: String,
    ) {
        if (ActivityCompat.checkSelfPermission(
                ctx,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(
                NOTIF_ID, NotificationCompat.Builder(ctx, CHANNEL_ID)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build()
            )
        }

    }
}