package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.Button

class Controller() {
    constructor(activity: Activity) : this() {
        val model = Model(activity)

        val startResetButton: Button = activity.requireViewById(R.id.start_reset_button)
        val pauseResumeButton: Button = activity.requireViewById<Button>(R.id.pause_resume_button)

        startResetButton.setOnClickListener {
            model.startOrResetGame()
        }

        pauseResumeButton.setOnClickListener {
            model.pauseOrResumeGame()
        }
    }
}