package com.skateboard.blockdetector

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Looper
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

/**
 * Created by skateboard on 2018/2/9.
 */
object NotificationUtil
{

    //    companion object
    //    {


    val BLOCKER_TITLE = "Blocker"

    val CHANNEL_NAME = BLOCKER_TITLE

    val CHANNEL_ID = "1001"

    var notifyId: Int = 1

    val REQUEST_CODE = 1001

    val KEY_CONTENT = "key_content"

    @Volatile
    private var notificationManager: NotificationManager? = null


    fun notify(context: Context,stackTrace:String)
    {
        createNotificationManager(context)

        if (Build.VERSION.SDK_INT >= 26)
        {
            createChannel(CHANNEL_ID, CHANNEL_NAME)

            notifyNotification(context, CHANNEL_ID,stackTrace)
        }
        else
        {
            notifyNotification(context,contentText=stackTrace)
        }


    }

    fun createNotificationManager(context: Context)
    {

        if (notificationManager == null)
        {
            @Synchronized
            if (notificationManager == null)
            {
                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
        }

    }

    @RequiresApi(value = Build.VERSION_CODES.O)
    fun createChannel(channelId: String, channelName: String): NotificationChannel
    {

        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.enableVibration(true)
        notificationManager?.createNotificationChannel(channel)
        return channel
    }


    private fun notifyNotification(context: Context, channelId: String = "",contentText:String)
    {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)

        notificationBuilder.setContentText(contentText)

        val intent = Intent(context, ShowActivity::class.java)

        intent.putExtra(KEY_CONTENT, contentText)

        val pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher)

        notificationBuilder.setContentTitle(BLOCKER_TITLE)

        notificationBuilder.setAutoCancel(true)

        notificationBuilder.setContentIntent(pendingIntent)

        notificationManager?.notify(notifyId, notificationBuilder.build())

        notifyId++

    }


    private fun buildStackTrace(): String
    {
        val stackTrace = Looper.getMainLooper().thread.stackTrace

        val strBuilder = StringBuilder()

        for (element in stackTrace)
        {

            strBuilder.append("${element.className} ${element.methodName} (line:${element.lineNumber})")

            strBuilder.append("\n")
        }

        return strBuilder.toString()
    }


    //    }


}