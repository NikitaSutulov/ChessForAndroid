package com.nikitasutulov.chessforandroid

import android.util.Log
import android.widget.Button

class Cell(button: Button, piece: Piece?, board: Board) {
    val button = button
    var piece = piece
    val board = board
    private val cells = board.getCells()
    private var x: Int? = null
    private var y: Int? = null

    fun setCoords(xCoord: Int, yCoord: Int) {
        if (x == null && y == null) {
            x = xCoord
            y = yCoord
        } else {
            Log.e("Cell", "x and y already assigned")
        }
    }

    fun getX() = x
    fun getY() = y

    fun getPossibleMoves(): MutableList<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()
        if (piece == null) {
            return possibleMoves
        }
        when (piece!!::class.java.simpleName) {
            "Pawn" -> {
                if (piece!!.color == Board.WHITE) {
                    if (x!! != 7) {
                        if (checkCellForFreeSpace(cells[x!! + 1][y!!]!!) && checkPawnStraightMove(cells[x!! + 1][y!!]!!)) {
                            possibleMoves.add(Pair(x!! + 1, y!!))
                        }
                    }
                    if (x!! != 7 && y!! != 7 && cells[x!! + 1][y!! + 1]!!.piece != null && cells[x!! + 1][y!! + 1]!!.piece?.color == Board.BLACK) {
                        if (checkCellForFreeSpace(cells[x!! + 1][y!! + 1]!!)) {
                            possibleMoves.add(Pair(x!! + 1, y!! + 1))
                        }
                    }
                    if (x!! != 7 && y!! != 0 && cells[x!! + 1][y!! - 1]!!.piece != null && cells[x!! + 1][y!! - 1]!!.piece?.color == Board.BLACK) {
                        if (checkCellForFreeSpace(cells[x!! + 1][y!! - 1]!!)) {
                            possibleMoves.add(Pair(x!! + 1, y!! - 1))
                        }
                    }
                    if (!piece!!.getIsMoved() && x!! < 6) {
                        if (checkCellForFreeSpace(cells[x!! + 2][y!!]!!)) {
                            possibleMoves.add(Pair(x!! + 2, y!!))
                        }
                    }
                } else if (piece!!.color == Board.BLACK) {
                    if (x!! != 0) {
                        if (checkCellForFreeSpace(cells[x!! - 1][y!!]!!) && checkPawnStraightMove(cells[x!! - 1][y!!]!!)) {
                            possibleMoves.add(Pair(x!! - 1, y!!))
                        }
                    }
                    if (x!! != 0 && y!! != 0 && cells[x!! - 1][y!! - 1]!!.piece != null && cells[x!! - 1][y!! - 1]!!.piece?.color == Board.WHITE) {
                        if (checkCellForFreeSpace(cells[x!! - 1][y!! - 1]!!)) {
                            possibleMoves.add(Pair(x!! - 1, y!! - 1))
                        }
                    }
                    if (x!! != 0 && y!! != 7 && cells[x!! - 1][y!! + 1]!!.piece != null && cells[x!! - 1][y!! + 1]!!.piece?.color == Board.WHITE) {
                        if (checkCellForFreeSpace(cells[x!! - 1][y!! + 1]!!)) {
                            possibleMoves.add(Pair(x!! - 1, y!! + 1))
                        }
                    }
                    if (!piece!!.getIsMoved() && x!! > 1) {
                        if (checkCellForFreeSpace(cells[x!! - 2][y!!]!!)) {
                            possibleMoves.add(Pair(x!! - 2, y!!))
                        }
                    }
                }
            }
            "Rook" -> {
                castVerticalHorizontal(possibleMoves)
            }
            "Knight" -> {
                if (coordsInRange(x!! - 2, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[x!! - 2][y!! + 1]!!)) {
                        possibleMoves.add(Pair(x!! - 2, y!! + 1))
                    }
                }
                if (coordsInRange(x!! - 2, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[x!! - 2][y!! - 1]!!)) {
                        possibleMoves.add(Pair(x!! - 2, y!! - 1))
                    }
                }
                if (coordsInRange(x!! + 2, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[x!! + 2][y!! + 1]!!)) {
                        possibleMoves.add(Pair(x!! + 2, y!! + 1))
                    }
                }
                if (coordsInRange(x!! + 2, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[x!! + 2][y!! - 1]!!)) {
                        possibleMoves.add(Pair(x!! + 2, y!! - 1))
                    }
                }
                if (coordsInRange(x!! - 1, y!! + 2)) {
                    if (checkCellForFreeSpace(cells[x!! - 1][y!! + 2]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!! + 2))
                    }
                }
                if (coordsInRange(x!! + 1, y!! + 2)) {
                    if (checkCellForFreeSpace(cells[x!! + 1][y!! + 2]!!)) {
                        possibleMoves.add(Pair(x!! + 1, y!! + 2))
                    }
                }
                if (coordsInRange(x!! - 1, y!! - 2)) {
                    if (checkCellForFreeSpace(cells[x!! - 1][y!! - 2]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!! - 2))
                    }
                }
                if (coordsInRange(x!! + 1, y!! - 2)) {
                    if (checkCellForFreeSpace(cells[x!! + 1][y!! - 2]!!)) {
                        possibleMoves.add(Pair(x!! + 1, y!! - 2))
                    }
                }
            }
            "Bishop" -> {
                castDiagonal(possibleMoves)
            }
            "Queen" -> {
                castVerticalHorizontal(possibleMoves)
                castDiagonal(possibleMoves)
            }
            "King" -> {
                if (coordsInRange(x!! - 1, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[x!! - 1][y!! + 1]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!! + 1))
                    }
                }
                if (coordsInRange(x!! - 1, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[x!! - 1][y!! - 1]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!! - 1))
                    }
                }
                if (coordsInRange(x!! + 1, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[x!! + 1][y!! + 1]!!)) {
                        possibleMoves.add(Pair(x!! + 1, y!! + 1))
                    }
                }
                if (coordsInRange(x!! + 1, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[x!! + 1][y!! - 1]!!)) {
                        possibleMoves.add(Pair(x!! + 1, y!! - 1))
                    }
                }
                if (coordsInRange(x!! - 1, y!!)) {
                    if (checkCellForFreeSpace(cells[x!! - 1][y!!]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!!))
                    }
                }
                if (coordsInRange(x!! + 1, y!!)) {
                    if (checkCellForFreeSpace(cells[x!! + 1][y!!]!!)) {
                        possibleMoves.add(Pair(x!! + 1, y!!))
                    }
                }
                if (coordsInRange(x!!, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[x!!][y!! - 1]!!)) {
                        possibleMoves.add(Pair(x!!, y!! - 1))
                    }
                }
                if (coordsInRange(x!!, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[x!!][y!! + 1]!!)) {
                        possibleMoves.add(Pair(x!!, y!! + 1))
                    }
                }
            }
        }
        return possibleMoves
    }

    fun castVerticalHorizontal(possibleMoves: MutableList<Pair<Int, Int>>) {
        for (i in (x!! + 1)..7) {
            if (coordsInRange(i, y!!)) {
                if (!checkCellForFreeSpace(cells[i][y!!]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, y!!))
            }
        }
        for (i in (x!! - 1) downTo 0) {
            if (coordsInRange(i, y!!)) {
                if (!checkCellForFreeSpace(cells[i][y!!]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, y!!))
            }
        }
        for (j in (y!! + 1)..7) {
            if (coordsInRange(x!!, j)) {
                if (!checkCellForFreeSpace(cells[x!!][j]!!)) {
                    break
                }
                possibleMoves.add(Pair(x!!, j))
            }
        }
        for (j in (y!! - 1) downTo 0) {
            if (coordsInRange(x!!, j)) {
                if (!checkCellForFreeSpace(cells[x!!][j]!!)) {
                    break
                }
                possibleMoves.add(Pair(x!!, j))
            }
        }
    }

    fun castDiagonal(possibleMoves: MutableList<Pair<Int, Int>>) {
        var j = y!! + 1
        for (i in (x!! + 1)..7) {
            if (coordsInRange(i, j)) {
                if (!checkCellForFreeSpace(cells[i][j]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, j))
            }
            if (j < 7) {
                j++
            } else {
                break
            }
        }

        j = y!! - 1
        for (i in (x!! + 1)..7) {
            if (coordsInRange(i, j)) {
                if (!checkCellForFreeSpace(cells[i][j]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, j))
            }
            if (j > 0) {
                j--
            } else {
                break
            }
        }

        j = y!! + 1
        for (i in (x!! - 1) downTo 0) {
            if (coordsInRange(i, j)) {
                if (!checkCellForFreeSpace(cells[i][j]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, j))
            }
            if (j < 7) {
                j++
            } else {
                break
            }
        }

        j = y!! - 1
        for (i in (x!! - 1) downTo 0) {
            if (coordsInRange(i, j)) {
                if (!checkCellForFreeSpace(cells[i][j]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, j))
            }
            if (j > 0) {
                j--
            } else {
                break
            }
        }
    }

    private fun coordsInRange(x: Int, y: Int): Boolean {
        return (x in 0..7 && y in 0..7)
    }

    private fun checkCellForFreeSpace(cell: Cell): Boolean {
        if (cell.piece == null) {
            return true
        }
        if (cell.piece!!.color == Board.WHITE && piece!!.color == Board.BLACK ||
            cell.piece!!.color == Board.BLACK && piece!!.color == Board.WHITE
        ) {
            return true
        }
        return false
    }

    private fun checkPawnStraightMove(cell: Cell): Boolean {
        if (cell.piece != null) {
            return false
        }
        return true
    }

    fun promotePawn() {
        piece = Queen(piece!!.color)
    }

    fun getSurroundingCells(surroundingCells: MutableList<Cell>) {
        for (i in (getX()!! - 1)..(getX()!! + 1)) {
            for (j in (getY()!! - 1)..(getY()!! + 1)) {
                if (i != j && coordsInRange(i, j) && checkCellForFreeSpace(cells[i][j]!!)) {
                    surroundingCells.add(cells[i][j]!!)
                }
            }
        }
    }

    fun isUnderAttack(enemyCells: MutableList<Cell>): Boolean {
        return enemyCells.any { cell -> cell.getPossibleMoves().any { move -> move.first == getX()!! && move.second == getY()!! } }
    }
}