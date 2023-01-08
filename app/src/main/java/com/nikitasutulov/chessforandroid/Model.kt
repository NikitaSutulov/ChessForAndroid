package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class Model() {
    private lateinit var activity: Activity
    private var isGameStarted = false
    private var isGamePaused = true
    private lateinit var boardGrid: GridLayout
    private lateinit var startResetButton: Button
    private lateinit var pauseResumeButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var board: Board
    private lateinit var serviceIntent: Intent
    private lateinit var updateTime: BroadcastReceiver
    private var time = 0.0

    constructor(activity: Activity) : this() {
        this.activity = activity
        boardGrid = activity.requireViewById<GridLayout>(R.id.board_grid)
        startResetButton = activity.requireViewById<Button>(R.id.start_reset_button)
        pauseResumeButton = activity.requireViewById<Button>(R.id.pause_resume_button)
        timerTextView = activity.requireViewById<TextView>(R.id.main_timer)
        serviceIntent = Intent(activity.applicationContext, TimerService::class.java)
        updateTime = object : BroadcastReceiver()
        {
            override fun onReceive(context: Context, intent: Intent)
            {
                time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
                timerTextView.text = getTimeStringFromDouble(time)
            }
        }
        this.activity.registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    private fun initBoard() {
        boardGrid.apply {
            alignmentMode = GridLayout.ALIGN_BOUNDS
            columnCount = 8
            rowCount = 8
        }
        var currentIndex = 0
        for (i in 0..7) {
            for (j in 0..7) {
                val cellButton = Button(activity)
                boardGrid.addView(cellButton, currentIndex++)

                val param: GridLayout.LayoutParams = GridLayout.LayoutParams().apply {
                    height = boardGrid.width / 8
                    width = boardGrid.width / 8
                    bottomMargin = 1
                    leftMargin = 0
                    topMargin = 0
                    rightMargin = 1
                    columnSpec = GridLayout.spec(j)
                    rowSpec = GridLayout.spec(7 - i)
                }

                cellButton.apply {
                    textSize = 5f
                    layoutParams = param
                    setOnClickListener {

                    }
                }
            }
        }
        board = Board(activity)
        board.init(boardGrid, this)
        board.show()
    }

    private fun clearBoard() {
        val boardGrid: GridLayout = activity.findViewById(R.id.board_grid)
        boardGrid.removeAllViews()
    }


    fun startOrResetGame() {
        if (!isGameStarted) {
            startGame()
        } else {
            resetGame()
        }
    }

    private fun resetGame() {
        isGameStarted = false
        startResetButton.text = "Start"
        pauseGame()
        pauseResumeButton.text = "Pause"
        pauseResumeButton.isEnabled = false
        clearBoard()
        resetTimer()
    }

    private fun startGame() {
        isGameStarted = true
        startResetButton.text = "Reset"
        resumeGame()
        pauseResumeButton.isEnabled = true
        initBoard()
        startTimer()
    }

    fun pauseOrResumeGame() {
        if (isGamePaused) {
            resumeGame()
        } else {
            pauseGame()
        }
    }

    private fun pauseGame() {
        isGamePaused = true
        pauseResumeButton.text = "Resume"
        Toast.makeText(activity, "Paused", Toast.LENGTH_SHORT).show()
        stopTimer()
    }

    private fun resumeGame() {
        isGamePaused = false
        pauseResumeButton.text = "Pause"
        Toast.makeText(activity, "Resumed", Toast.LENGTH_SHORT).show()
        startTimer()
    }

    private fun resetTimer()
    {
        time = 0.0
        timerTextView.text = getTimeStringFromDouble(time)
        Log.d("Timer", "Reset!")
    }

    private fun startTimer()
    {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        activity.startService(serviceIntent)
        Log.d("Timer", "Started!")
    }

    private fun stopTimer()
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