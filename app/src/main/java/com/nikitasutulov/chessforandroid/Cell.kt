package com.nikitasutulov.chessforandroid

import android.util.Log
import android.widget.Button

class Cell(button: Button, piece: Piece?, board: Board) {
    val button = button
    var piece = piece
    val board = board
    val cells = board.getCells()
    private var x: Int? = null
    private var y: Int? = null
    var isHiglighted = false

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

    fun getPossibleMoves(): Array<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()
        if (piece == null) {
            return possibleMoves.toTypedArray()
        }
        when (piece!!::class.java.simpleName) {
            "Pawn" -> {
                if (piece!!.color == "WHITE") {
                    if (x!! != 7) {
                        possibleMoves.add(Pair(x!! + 1, y!!))
                    }
                    if (x!! != 7 && y!! != 7) {
                        possibleMoves.add(Pair(x!! + 1, y!! + 1))
                    }
                    if (x!! != 7 && y!! != 0) {
                        possibleMoves.add(Pair(x!! + 1, y!! - 1))
                    }
                    if (!piece!!.isMoved && x!! != 6) {
                        possibleMoves.add(Pair(x!! + 2, y!!))
                    }
                } else if (piece!!.color == "BLACK") {
                    if (x!! != 0) {
                        possibleMoves.add(Pair(x!! - 1, y!!))
                    }
                    if (x!! != 0 && y!! != 0) {
                        possibleMoves.add(Pair(x!! - 1, y!! - 1))
                    }
                    if (x!! != 0 && y!! != 7) {
                        possibleMoves.add(Pair(x!! - 1, y!! + 1))
                    }
                    if (!piece!!.isMoved && x!! > 1) {
                        possibleMoves.add(Pair(x!! - 2, y!!))
                    }
                }
            }
            "Rook" -> {
                verticalHorizontal(possibleMoves)
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
                diagonal(possibleMoves)
            }
            "Queen" -> {
                verticalHorizontal(possibleMoves)
                diagonal(possibleMoves)
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
        return possibleMoves.toTypedArray()
            .also { it.map { pair -> Log.d("Possible moves", board.getChessCoords(pair.first, pair.second)) } }
    }

    private fun verticalHorizontal(possibleMoves: MutableList<Pair<Int, Int>>) {
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

    private fun diagonal(possibleMoves: MutableList<Pair<Int, Int>>) {
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

    fun coordsInRange(x: Int, y: Int): Boolean {
        return (x in 0..7 && y in 0..7)
    }

    fun checkCellForFreeSpace(cell: Cell): Boolean {
        if (cell.piece == null) {
            cell.isHiglighted = true
            return true
        }
        if ((cell.piece!!.color == "WHITE" && piece!!.color == "BLACK")
            || (cell.piece!!.color == "BLACK" && piece!!.color == "WHITE")) {
            cell.isHiglighted = true
            return true
        }
        return false
    }
}