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
    private var isMate = false

    fun init(gridLayout: GridLayout, model: Model) {
        this.gridLayout = gridLayout
        this.gameModel = model
        isCheck = false
        isMate = false
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
        val colorCells = mutableListOf<Cell>()
        for (i in 0..7) {
            for (j in 0..7) {
                if (cells[i][j]!!.piece?.color == color) {
                    colorCells.add(cells[i][j]!!)
                }
            }
        }
        return colorCells
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
                if (cells[i][j]!!.piece != null) {
                    cells[i][j]!!.button.setBackgroundResource(cells[i][j]!!.piece!!.getDrawableID())
                } else {
                    cells[i][j]!!.button.setBackgroundResource(R.drawable.transparent)
                }
            }
        }
    }

    private fun getChessCoords(x: Int, y: Int): String {
        return "${(x.toChar() + 97)}${y + 1}"
    }

    fun getCells() = cells

    private fun doMove(cell: Cell) {
//        isMate = checkForMate(currentTeam)
//        if (!isMate) {
//            cancelCheck()
//        } else {
//            onGameOver()
//            return
//        }
        if (!isMoveStarted && cell.piece != null && cell.piece?.color == currentTeam) {
            selectedCell = cell
            possibleMoves = selectedCell!!.getPossibleMoves()
            isMoveStarted = true
            moveTimer.start()
        } else if (isMoveStarted && cell.piece != null && cell.piece?.color == currentTeam) {
            selectedCell = cell
            possibleMoves = selectedCell!!.getPossibleMoves()
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

    private fun onGameOver() {
        Toast.makeText(activity, "Mate! Game over!", Toast.LENGTH_SHORT).show()
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
        if (cell.piece is Pawn) {
            checkForPromotion(cell)
        }
        cell.piece!!.setIsMoved()
        if (currentTeam == WHITE) {
            Collections.replaceAll(whiteCells, selectedCell, cell)
        } else {
            Collections.replaceAll(blackCells, selectedCell, cell)
        }
        val enemyTeam = if (currentTeam == WHITE) {
            BLACK
        } else {
            WHITE
        }
        listenMate(currentTeam)
        listenMate(enemyTeam)
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

    private fun isCheckFromCell(cells: MutableList<Cell>, moves: MutableList<Pair<Int, Int>>): Boolean {
        return (moves.any { move -> cells.find { cell -> cell.getX()!! == move.first && cell.getY()!! == move.second }?.piece is King })
    }

    private fun handleAllChecks() {
        val currentTeamCells = if (currentTeam == WHITE) whiteCells else blackCells
        val enemyTeamCells = if (currentTeam == WHITE) blackCells else whiteCells
        var isAnyCheck = false
        for (cell: Cell in currentTeamCells) {
            if (isCheckFromCell(enemyTeamCells, cell.getPossibleMoves())) {
                isAnyCheck = true
                isCheck = true
                Log.d("Check", "from cell ${getChessCoords(cell.getX()!!, cell.getY()!!)}")
                Toast.makeText(activity, "Check", Toast.LENGTH_SHORT).show()
                if (threatingCells.find { cellToFind -> cellToFind === cell } == null)
                    threatingCells.add(cell)
            }
        }
        if (!isAnyCheck) {
            cancelCheck()
        }
    }

    private fun cancelCheck() {
        isCheck = false
        threatingCells = mutableListOf()
    }

    private fun checkForMate(team: String): Boolean {
        if (isCheck) {
            return true
        }
        handleAllChecks()
        if (!isCheck) {
            return false
        }
        val teamCells: MutableList<Cell>
        val enemyCells: MutableList<Cell>
        if (team == WHITE) {
            teamCells = whiteCells
            enemyCells = blackCells
        } else {
            teamCells = blackCells
            enemyCells = whiteCells
        }
        val teamKingCell = teamCells.find { cell -> cell.piece is King }!!
        val surroundingCells = mutableListOf<Cell>()
        teamKingCell.getSurroundingCells(surroundingCells)
        if (surroundingCells.all { cell -> cell.isUnderAttack(enemyCells) }) {
            return true
        }
        if (threatingCells.size > 1) {
            return true
        }
        if (threatingCells[0].isUnderAttack(teamCells)) {
            Log.d("Checking for mate", "problem is here")
            return false
        }
        if (threatingCells[0].piece is Knight) {
            return true
        }
        if (surroundingCells.any { cell -> cell === threatingCells[0] }) {
            return true
        }
        val kingThreatWays = mutableListOf<Pair<Int, Int>>()
        teamKingCell.castDiagonal(kingThreatWays)
        teamKingCell.castVerticalHorizontal(kingThreatWays)
        if (teamCells.any { cell -> cell.getPossibleMoves().any { move -> kingThreatWays.any { way -> move == way } } }) {
            return false
        }
        return true
    }

    private fun listenMate(team: String) {
        isMate = checkForMate(team)
        Log.d("Checking for mate", "$team : $isMate")
        if (!isMate) {
            cancelCheck()
        } else {
            onGameOver()
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