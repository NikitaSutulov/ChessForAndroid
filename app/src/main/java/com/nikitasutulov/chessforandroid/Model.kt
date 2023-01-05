package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

class Model(activity: Activity) {
    private val activity = activity
    private var isGameStarted = false
    private lateinit var board: Board

    private fun initBoard() {
        val boardGrid: GridLayout = activity.findViewById(R.id.board_grid)
        boardGrid.alignmentMode = GridLayout.ALIGN_BOUNDS
        boardGrid.columnCount = 8
        boardGrid.rowCount = 8
        for (i in 1..8) {
            for (j in 1..8) {
                val cellButton = Button(activity)
                cellButton.textSize = 5f
                boardGrid.addView(cellButton, i * j - 1)
                val param: GridLayout.LayoutParams = GridLayout.LayoutParams()
                param.height = boardGrid.width / 8
                param.width = boardGrid.width / 8
                param.bottomMargin = 1
                param.leftMargin = 0
                param.topMargin = 0
                param.rightMargin = 1
                param.columnSpec = GridLayout.spec(i - 1)
                param.rowSpec = GridLayout.spec(j - 1)
                cellButton.layoutParams = param
//                cellButton.setBackgroundResource(R.drawable.b_queen)
                cellButton.setOnClickListener {
                    val toast = Toast.makeText(activity, "You clicked on button $i $j!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        board = Board(activity)
        board.init(boardGrid)
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