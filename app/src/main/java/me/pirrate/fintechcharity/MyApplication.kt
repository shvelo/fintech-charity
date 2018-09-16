package me.pirrate.fintechcharity

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build



class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Globals.restore(this)
        initChannels()
    }

    fun initChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("default",
                "Charity",
                NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Channel description"
        notificationManager.createNotificationChannel(channel)
    }

}