package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.Button
import android.widget.GridLayout

class Model(activity: Activity) {
    private val activity = activity
    private var isGameStarted = false

    private fun initBoard() {
        val boardGrid: GridLayout = activity.findViewById(R.id.board_grid)
        boardGrid.alignmentMode = GridLayout.ALIGN_BOUNDS
        boardGrid.columnCount = 8
        boardGrid.rowCount = 8
        for (i in 0..7) {
            for (j in 0..7) {
                val cellButton = Button(activity)
                cellButton.text = "$i $j"
                cellButton.textSize = 5f
                boardGrid.addView(cellButton, i * j)
                val param: GridLayout.LayoutParams = GridLayout.LayoutParams()
                param.height = boardGrid.width / 8
                param.width = boardGrid.width / 8
                param.bottomMargin = 1
                param.leftMargin = 0
                param.topMargin = 0
                param.rightMargin = 1
                param.columnSpec = GridLayout.spec(i)
                param.rowSpec = GridLayout.spec(j)
                cellButton.layoutParams = param
                cellButton.setBackgroundResource(R.drawable.b_queen)
            }
        }
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
        isGameStarted = !isGameStarted
    }

    private fun resetGame() {
        clearBoard()
    }

    private fun startGame() {
        initBoard()
    }

    fun swapButtonText(button: Button, str1: String, str2: String) {
        if (button.text.toString() == str1) {
            button.text = str2
        } else {
            button.text = str1
        }
    }

    fun pauseOrResumeGame() {
    //TODO: pause or resume game
    }
}