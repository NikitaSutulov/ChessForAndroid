package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.Button

class Controller () {
    constructor(activity: Activity) : this() {
        val model = Model(activity)

        val startResetButton: Button = activity.requireViewById(R.id.start_reset_button)
        startResetButton.setOnClickListener {
            model.startOrResetGame()
            model.swapButtonText(startResetButton, "Start", "Reset")
        }

        val pauseResumeButton: Button = activity.requireViewById(R.id.pause_resume_button)
        pauseResumeButton.setOnClickListener {
            model.pauseOrResumeGame()
            model.swapButtonText(pauseResumeButton, "Pause", "Resume")
        }
    }
}