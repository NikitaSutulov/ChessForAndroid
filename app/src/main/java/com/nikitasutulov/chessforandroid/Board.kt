package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.util.Log
import android.widget.*
import java.util.*

class Board(activity: Activity, currentMoveTV: TextView) {
    private val activity = activity
    private val moveTimer = MoveTimer(activity, currentMoveTV)
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
    private lateinit var whiteCells: MutableList<Cell>
    private lateinit var blackCells: MutableList<Cell>
    private var threatingCells = mutableListOf<Cell>()
    private lateinit var gridLayout: GridLayout
    private lateinit var gameModel: Model

    private val startMap = arrayOf(
        arrayOf<Piece?>(Rook(WHITE), Knight(WHITE), Bishop(WHITE), Queen(WHITE), King(WHITE), Bishop(WHITE), Knight(WHITE), Rook(WHITE)),
        arrayOf<Piece?>(Pawn(WHITE), Pawn(WHITE), Pawn(WHITE), Pawn(WHITE), Pawn(WHITE), Pawn(WHITE), Pawn(WHITE), Pawn(WHITE)),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(null, null, null, null, null, null, null, null),
        arrayOf<Piece?>(Pawn(BLACK), Pawn(BLACK), Pawn(BLACK), Pawn(BLACK), Pawn(BLACK), Pawn(BLACK), Pawn(BLACK), Pawn(BLACK)),
        arrayOf<Piece?>(Rook(BLACK), Knight(BLACK), Bishop(BLACK), Queen(BLACK), King(BLACK), Bishop(BLACK), Knight(BLACK), Rook(BLACK)),
    )

    private var selectedCell: Cell? = null
    private var currentTeam = WHITE
    private var possibleMoves = mutableListOf<Pair<Int, Int>>()
    private var isMoveStarted = false
    private var isCheck = false

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
        whiteCells = setCellsList(WHITE)
        blackCells = setCellsList(BLACK)
        setCellButtonsOnClickListeners()
        moveTimer.start()
    }

    private fun setCellsList(color: String): MutableList<Cell> {
        val whiteCells = mutableListOf<Cell>()
        for (i in 0..7) {
            for (j in 0..7) {
                if (cells[i][j]!!.piece?.color == color) {
                    whiteCells.add(cells[i][j]!!)
                }
            }
        }
        return whiteCells
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

    fun switchButtons(isGamePaused: Boolean) {
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

    private fun doMove(cell: Cell) {
        if (!isMoveStarted && cell.piece != null && cell.piece?.color == currentTeam) {
            selectedCell = cell
            possibleMoves = selectedCell!!.getPossibleMoves().toMutableList()
            isMoveStarted = true
            moveTimer.start()
        } else if (isMoveStarted && cell.piece != null && cell.piece?.color == currentTeam) {
            selectedCell = cell
            possibleMoves = selectedCell!!.getPossibleMoves().toMutableList()
        } else if (isMoveStarted && cell.piece?.color != currentTeam) {
            if (possibleMoves.any { pair -> pair.first == cell.getX() && pair.second == cell.getY() }) {
                activity.requireViewById<TextView>(R.id.last_move_tv).apply {
                    text = "${getChessCoords(selectedCell!!.getX()!!, selectedCell!!.getY()!!)} ${getChessCoords(cell.getX()!!, cell.getY()!!)}"
                }
                movePiece(selectedCell!!, cell)
                selectedCell = null
                isMoveStarted = false
                switchCurrentTeam()
                resetMoveTimer()
                moveTimer.start()
            }
        }
    }

    private fun movePiece(selectedCell: Cell, cell: Cell) {
        if (cell.piece == null) {
            cell.piece = selectedCell.piece
            selectedCell.piece = null
        } else {
            cell.piece = selectedCell.piece
            selectedCell.piece = null
            if (currentTeam == WHITE) {
                blackCells.remove(cell)
            } else {
                whiteCells.remove(cell)
            }
        }
        if (cell.piece!!::class.java.simpleName == "Pawn") {
            checkForPromotion(cell)
        }
        cell.piece!!.setIsMoved()
        if (currentTeam == WHITE) {
            Collections.replaceAll(whiteCells, selectedCell, cell)
        } else {
            Collections.replaceAll(blackCells, selectedCell, cell)
        }
        handleAllChecks()
    }

    private fun checkForPromotion(cell: Cell) {
        if (cell.piece!!.color == WHITE && cell.getX() == 7 ||
            cell.piece!!.color == BLACK && cell.getX() == 0
        ) {
            cell.promotePawn()
            show()
        }
    }

    private fun switchCurrentTeam() {
        currentTeam = if (currentTeam == WHITE) {
            BLACK
        } else {
            WHITE
        }
        activity.requireViewById<TextView>(R.id.current_move_tv).apply {
            text = currentTeam + "\n"
        }
    }

    private fun isCheck(moves: MutableList<Pair<Int, Int>>): Boolean {
        return (moves.any { move -> cells[move.first][move.second]!!.piece != null && cells[move.first][move.second]!!.piece!!::class.java.simpleName == "King" })
    }

    private fun handleAllChecks() {
        val currentTeamCells = if (currentTeam == WHITE) whiteCells else blackCells
        for (cell: Cell in currentTeamCells) {
            if (isCheck(cell.getPossibleMoves().toMutableList())) {
                Log.d("Check", "from cell ${getChessCoords(cell.getX()!!, cell.getY()!!)}")
                threatingCells.add(cell)
            }
        }
    }

    fun resetMoveTimer() {
        moveTimer.stop()
        moveTimer.reset()
    }

    fun pauseMoveTimer() {
        moveTimer.stop()
    }

    fun resumeMoveTimer() {
        moveTimer.start()
    }

    companion object {
        const val WHITE = "WHITE"
        const val BLACK = "BLACK"
    }
}