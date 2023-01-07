package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

class Model(activity: Activity) {
    private val activity = activity
    private var isGameStarted = false
    private val boardGrid: GridLayout = activity.requireViewById<GridLayout>(R.id.board_grid)
    private lateinit var board: Board
    //private lateinit var currentCell: Cell

    private fun initBoard() {
        boardGrid.apply {
            alignmentMode = GridLayout.ALIGN_BOUNDS
            columnCount = 8
            rowCount = 8
        }
        for (i in 0..63) {
            val cellButton = Button(activity)
            boardGrid.addView(cellButton, i)

            val param: GridLayout.LayoutParams = GridLayout.LayoutParams().apply {
                height = boardGrid.width / 8
                width = boardGrid.width / 8
                bottomMargin = 1
                leftMargin = 0
                topMargin = 0
                rightMargin = 1
                columnSpec = GridLayout.spec(i % 8)
                rowSpec = GridLayout.spec(7 - i / 8)
            }

            cellButton.apply {
                textSize = 5f
                layoutParams = param
                setOnClickListener {
                    Toast.makeText(activity, "You clicked on cell ${indexToChessCoords(i)}!", Toast.LENGTH_SHORT).show()
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

    fun indexToChessCoords(i: Int): String {
        val x = i % 8
        val y = i / 8 + 1
        val xToChessCoord: String = (x.toChar() + 97).toString()
        return "$xToChessCoord$y"
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