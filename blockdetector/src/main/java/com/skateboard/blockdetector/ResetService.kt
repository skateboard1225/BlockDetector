package com.skateboard.blockdetector

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat

/**
 * Created by skateboard on 2018/2/9.
 */
class ResetService : Service()
{

    val FOREGROUND_ID = Integer.MAX_VALUE

    val RESET_TITLE = "重置阈值"

    val RESET_CONTENT = "点击来重置阈值"

    val REQUEST_ID = 9900

    val CHANNEL_ID = "1001"

    val CHANNEL_NAME = "Blocker"

    override fun onBind(intent: Intent?): IBinder?
    {
        return null
    }

    override fun onCreate()
    {
        super.onCreate()


        val notification=createNotification()

        startForeground(FOREGROUND_ID, notification)

        println("already called startForground")
    }


    private fun createNotification(): Notification
    {

        if(Build.VERSION.SDK_INT>=26)
        {
            NotificationUtil.createChannel(CHANNEL_ID, CHANNEL_NAME)
        }

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher)

        notificationBuilder.setContentTitle(RESET_TITLE)

        notificationBuilder.setWhen(System.currentTimeMillis())

        notificationBuilder.setAutoCancel(false)

        notificationBuilder.setContentText(RESET_CONTENT)

        val intent = Intent(this, ResetActivity::class.java)

        val pendingInt = PendingIntent.getActivity(this, REQUEST_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder.setContentIntent(pendingInt)

        return notificationBuilder.build()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        stopForeground(true)
        stopSelf()
    }
}