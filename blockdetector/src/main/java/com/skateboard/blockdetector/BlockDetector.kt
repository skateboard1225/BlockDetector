package com.skateboard.blockdetector

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.text.format.DateFormat
import java.util.*

/**
 * Created by skateboard on 2018/2/8.
 */
object BlockDetector
{


    private lateinit var handlerThread: HandlerThread

    private val HANDLER_THTREAD_NAME = "blockdetector"

    private lateinit var notifyHandler: Handler

    private var LIMIT_TIME = 16L


    fun resetLimitTime(limitTime: Long)
    {
        LIMIT_TIME = limitTime
    }

    fun install(context: Context, limitTime: Long = LIMIT_TIME)
    {

        LIMIT_TIME = limitTime

        startForegroundService(context)

        preparedHandlerThread()

        initNotifyHandler(context)

        Looper.getMainLooper().setMessageLogging { x ->
            if (x != null)
            {

                if (x.contains(">>>>> Dispatching to "))
                {

                    notifyHandler.postDelayed(notifyRunnable, LIMIT_TIME)

                }
                else if (x.contains("<<<<< Finished to "))
                {
                    notifyHandler.removeCallbacks(notifyRunnable)
                }

            }
        }

    }

    private val notifyRunnable = Runnable {

        notifyHandler.sendEmptyMessage(10)

    }


    private fun preparedHandlerThread()
    {
        handlerThread = HandlerThread(HANDLER_THTREAD_NAME)
        handlerThread.start()
    }

    private fun initNotifyHandler(context: Context)
    {
        notifyHandler = Handler(handlerThread.looper)
        {

            val staceTrack = SaveUtil.buildStackTrace()
            SaveUtil.saveStackTrace(context.applicationContext, generateStackTraceKey(), staceTrack)
            NotificationUtil.notify(context, staceTrack)
            true
        }
    }

    private fun generateStackTraceKey(): String
    {
        val date = Date()

        val key = DateFormat.format("yyyy-MM-dd HH:mm:ss", date)

        return key.toString()
    }


    private fun startForegroundService(context: Context)
    {

        val intent = Intent(context, ResetService::class.java)

        if (Build.VERSION.SDK_INT >= 26)
        {
            ContextCompat.startForegroundService(context, intent)
        }
        else
        {
            context.startService(intent)
        }


    }




}