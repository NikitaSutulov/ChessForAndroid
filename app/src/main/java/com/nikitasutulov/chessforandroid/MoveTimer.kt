package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.TextView
import kotlin.math.roundToInt

class MoveTimer() {
    private lateinit var activity: Activity
    private lateinit var timerTV: TextView
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var updateTime = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(MoveTimerService.TIME_EXTRA, 0.0)
            timerTV.text = getTimeStringFromDouble(time)
        }
    }

    constructor(activity: Activity, timerTV: TextView): this() {
        this.activity = activity
        this.timerTV = timerTV
        serviceIntent = Intent(activity.applicationContext, MoveTimerService::class.java)
        activity.registerReceiver(updateTime, IntentFilter(MoveTimerService.TIMER_UPDATED))
    }

    fun reset()
    {
        time = 0.0
        timerTV.text = getTimeStringFromDouble(time)
        Log.d("Timer", "Reset!")
    }

    fun start()
    {
        serviceIntent.putExtra(MoveTimerService.TIME_EXTRA, time)
        activity.startService(serviceIntent)
        Log.d("Timer", "Started!")
    }

    fun stop()
    {
        activity.stopService(serviceIntent)
        Log.d("Timer", "Stopped!")
    }

    private fun getTimeStringFromDouble(time: Double): String
    {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)

}