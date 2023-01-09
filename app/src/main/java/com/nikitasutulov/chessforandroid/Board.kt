package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ScrollView

class Board (activity: Activity) {
    private val activity = activity
    private val cells: Array<Array<Cell?>> = arrayOf(
        arrayOfNulls(8),
        arrayOfNulls(8),
        arrayOfNulls(8),
        arrayOfNulls(8),
        arrayOfNulls(8),
        arrayOfNulls(8),
        arrayOfNulls(8),
        arrayOfNulls(8)
    )
    private lateinit var gridLayout: GridLayout
    private lateinit var gameModel: Model

    private val startMap = arrayOf(
        arrayOf<Piece?>(Rook("WHITE"), Knight("WHITE"), Bishop("WHITE"), Queen("WHITE"), King("WHITE"), Bishop("WHITE"), Knight("WHITE"), Rook("WHITE")),
        arrayOf<Piece?>(Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE")),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK")),
        arrayOf<Piece?>(Rook("BLACK"), Knight("BLACK"), Bishop("BLACK"), Queen("BLACK"), King("BLACK"), Bishop("BLACK"), Knight("BLACK"), Rook("BLACK")),
    )

    private var selectedCell: Cell? = null
    private var currentTeam = "WHITE"
    private var possibleMoves = mutableListOf<Pair<Int, Int>>()
    private var isMoveStarted = false

    private var whiteDeadPieces: ScrollView = activity.requireViewById(R.id.white_dead_pieces_scrollview)
    private var blackDeadPieces: ScrollView = activity.requireViewById(R.id.black_dead_pieces_scrollview)

    fun init(gridLayout: GridLayout, model: Model) {
        this.gridLayout = gridLayout
        this.gameModel = model
        val cellsMutableList = mutableListOf<MutableList<Cell>>()
        var childCounter = 0
        for (i in 0..7) {
            for (j in 0..7) {
                val button = gridLayout.getChildAt(childCounter++)!! as Button
                val newCell = Cell(button, startMap[i][j], this)
                newCell.setCoords(i, j)
                cellsMutableList.add(mutableListOf())
                cellsMutableList[i].add(newCell)
            }
            cells[i] = cellsMutableList[i].toTypedArray()
        }
        setCellButtonsOnClickListeners()
    }

    private fun setCellButtonsOnClickListeners() {
        for (i in 0..7) {
            for (j in 0..7) {
                val cell = cells[i][j]!!
                cell.button.setOnClickListener {
                    Log.d("Cell click", "You clicked on cell ${getChessCoords(cell.getX()!!, cell.getY()!!)}!")
                    if (cell.piece != null) {
                        Log.d("Cell click", "The piece is ${cell.piece!!::class.java.simpleName}")
                    } else {
                        Log.d("Cell click", "There is no piece on this cell")
                    }
                    doMove(cell)
                    show()
                }
            }
        }
    }

    fun toggleButtons(isGamePaused: Boolean) {
        for (i in 0..7) {
            for (j in 0..7) {
                cells[i][j]!!.button!!.isClickable = !isGamePaused
            }
        }
    }

    fun show() {
        for (i in 0..7) {
            for (j in 0..7) {
                if (cells[i][j]!!.isHiglighted) {
                    if (cells[i][j]!!.piece != null) {
                        cells[i][j]!!.button.setBackgroundResource(cells[i][j]!!.piece!!.getHighlightedDrawableID())
                    } else {
                        cells[i][j]!!.button.setBackgroundResource(R.drawable.green)
                    }
                }
                if (cells[i][j]!!.piece != null) {
                    cells[i][j]!!.button.setBackgroundResource(cells[i][j]!!.piece!!.getDrawableID())
                } else {
                    cells[i][j]!!.button.setBackgroundResource(R.drawable.transparent)
                }
            }
        }
    }

    fun getChessCoords(x: Int, y: Int): String {
        return "${(x.toChar() + 97)}${y + 1}"
    }

    fun getCells() = cells

    fun doMove(cell: Cell) {
        if (!isMoveStarted && cell.piece != null && cell.piece?.color == currentTeam) {
            selectedCell = cell
            possibleMoves = selectedCell!!.getPossibleMoves().toMutableList()
            isMoveStarted = true
        } else if (isMoveStarted && cell.piece?.color != currentTeam) {
            if (possibleMoves.filter { pair -> pair.first == cell.getX() && pair.second == cell.getY()}.isNotEmpty()) {
                movePiece(selectedCell!!, cell)
                selectedCell = null
                isMoveStarted = false
                switchCurrentTeam()
            }
        }
    }

    private fun movePiece(selectedCell: Cell, cell: Cell) {
        if (cell.piece == null) {
            cell.piece = selectedCell.piece
            selectedCell.piece = null
        } else {
            if (currentTeam == "WHITE") {
//                blackDeadPieces.addView(ImageView(activity).apply {
//                    scaleType = ImageView.ScaleType.FIT_XY
//                    adjustViewBounds = true
//                    setImageResource(cell.piece!!.getDrawableID())
//                })
            } else {
//                whiteDeadPieces.addView(ImageView(activity).apply {
//                    scaleType = ImageView.ScaleType.FIT_XY
//                    adjustViewBounds = true
//                    setImageResource(cell.piece!!.getDrawableID())
//                })
            }
            cell.piece = selectedCell.piece
            selectedCell.piece = null
        }
    }

    private fun switchCurrentTeam() {
        currentTeam = if (currentTeam == "WHITE") {
            "BLACK"
        } else {
            "WHITE"
        }
    }
}