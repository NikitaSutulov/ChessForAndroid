package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.*

class Model() {
    private lateinit var activity: Activity
    private var isGameStarted = false
    private var isGamePaused = true
    private lateinit var boardGrid: GridLayout
    private lateinit var startResetButton: Button
    private lateinit var pauseResumeButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var board: Board
    private lateinit var timer: GameTimer

    constructor(activity: Activity) : this() {
        this.activity = activity
        boardGrid = activity.requireViewById<GridLayout>(R.id.board_grid)
        startResetButton = activity.requireViewById<Button>(R.id.start_reset_button)
        pauseResumeButton = activity.requireViewById<Button>(R.id.pause_resume_button)
        timerTextView = activity.requireViewById<TextView>(R.id.main_timer)
        timer = GameTimer(activity, timerTextView)
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
        board = Board(activity, activity.requireViewById(R.id.move_timer))
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
        clearGameInfo()
        clearDeadPiecesScrollViews()
        timer.reset()
        board.resetMoveTimer()
    }

    private fun clearGameInfo() {
        activity.requireViewById<TextView>(R.id.last_move_tv).apply {
            text = "\n"
        }
        activity.requireViewById<TextView>(R.id.current_move_tv).apply {
            text = "\n"
        }
    }

    private fun clearDeadPiecesScrollViews() {
        val whiteDeadPieces: ScrollView = activity.requireViewById(R.id.white_dead_pieces_scrollview)
        val blackDeadPieces: ScrollView = activity.requireViewById(R.id.black_dead_pieces_scrollview)
        whiteDeadPieces.removeAllViews()
        blackDeadPieces.removeAllViews()
    }

    private fun startGame() {
        isGameStarted = true
        startResetButton.text = "Reset"
        pauseResumeButton.isEnabled = true
        initBoard()
        resumeGame()
        timer.start()
    }

    fun pauseOrResumeGame() {
        if (isGamePaused) {
            resumeGame()
        } else {
            pauseGame()
        }
        board.switchButtons(isGamePaused)
    }

    private fun pauseGame() {
        isGamePaused = true
        pauseResumeButton.text = "Resume"
        Toast.makeText(activity, "Paused", Toast.LENGTH_SHORT).show()
        timer.stop()
        board.pauseMoveTimer()
    }

    private fun resumeGame() {
        isGamePaused = false
        pauseResumeButton.text = "Pause"
        Toast.makeText(activity, "Resumed", Toast.LENGTH_SHORT).show()
        timer.start()
        board.resumeMoveTimer()
    }
}