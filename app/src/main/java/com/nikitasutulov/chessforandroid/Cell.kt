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
    val isHiglighted = false

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

    fun getUnfilteredPossibleMoves(): Array<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()
        if (piece == null) {
            return possibleMoves.toTypedArray()
        }
        when (piece!!::class.java.simpleName) {
            "Pawn" -> {
                if (piece!!.color == "WHITE" && y!! != 7) {
                    possibleMoves.add(Pair(x!! + 1, y!!))
                    if (x!! != 7) {
                        possibleMoves.add(Pair(x!! + 1, y!! + 1))
                    }
                    if (x!! != 0) {
                        possibleMoves.add(Pair(x!! + 1, y!! - 1))
                    }
                    if (!piece!!.isMoved && y!! != 6) {
                        possibleMoves.add(Pair(x!! + 2, y!!))
                    }
                } else if (piece!!.color == "BLACK" && y!! != 0) {
                    possibleMoves.add(Pair(x!! + 1, y!!))
                    if (!piece!!.isMoved && y!! != 1) {
                        possibleMoves.add(Pair(x!! + 2, y!!))
                    }
                }
            }
            "Rook" -> {
                verticalHorizontal(possibleMoves)
            }
            "Knight" -> {
                if (coordsInRange(x!! - 2, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[y!! + 1][x!! - 2]!!)) {
                        possibleMoves.add(Pair(x!! - 2, y!! + 1))
                    }
                }
                if (coordsInRange(x!! - 2, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[y!! - 1][x!! - 2]!!)) {
                        possibleMoves.add(Pair(x!! - 2, y!! - 1))
                    }
                }
                if (coordsInRange(x!! + 2, y!! + 1)) {
                    if (checkCellForFreeSpace(cells[y!! + 1][x!! + 2]!!)) {
                        possibleMoves.add(Pair(x!! + 2, y!! + 1))
                    }
                }
                if (coordsInRange(x!! + 2, y!! - 1)) {
                    if (checkCellForFreeSpace(cells[y!! - 1][x!! + 2]!!)) {
                        possibleMoves.add(Pair(x!! + 2, y!! - 1))
                    }
                }
                if (coordsInRange(x!! - 1, y!! + 2)) {
                    if (checkCellForFreeSpace(cells[y!! + 2][x!! - 1]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!! + 2))
                    }
                }
                if (coordsInRange(x!! + 1, y!! + 2)) {
                    if (checkCellForFreeSpace(cells[y!! + 2][x!! + 1]!!)) {
                        possibleMoves.add(Pair(x!! + 1, y!! + 2))
                    }
                }
                if (coordsInRange(x!! - 1, y!! - 2)) {
                    if (checkCellForFreeSpace(cells[y!! - 2][x!! - 1]!!)) {
                        possibleMoves.add(Pair(x!! - 1, y!! - 2))
                    }
                }
                if (coordsInRange(x!! + 1, y!! - 2)) {
                    if (checkCellForFreeSpace(cells[y!! - 2][x!! + 1]!!)) {
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
                if (x!! <= 6) {
                    possibleMoves.add(Pair(x!! + 1, y!!))
                    if (y!! >= 1) {
                        possibleMoves.add(Pair(x!! + 1, y!! - 1))
                    }
                    if (y!! <= 6) {
                        possibleMoves.add(Pair(x!! + 1, y!! + 1))
                    }
                }
                if (y!! >= 1) {
                    possibleMoves.add(Pair(x!!, y!! - 1))
                }
                if (y!! <= 6) {
                    possibleMoves.add(Pair(x!!, y!! + 1))
                }
                if (x!! >= 1) {
                    possibleMoves.add(Pair(x!! - 1, y!!))
                    if (y!! >= 1) {
                        possibleMoves.add(Pair(x!! - 1, y!! - 1))
                    }
                    if (y!! <= 6) {
                        possibleMoves.add(Pair(x!! - 1, y!! + 1))
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
                if (!checkCellForFreeSpace(cells[y!!][i]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, y!!))
            }
        }
        for (i in (x!! - 1) downTo 0) {
            if (coordsInRange(i, y!!)) {
                if (!checkCellForFreeSpace(cells[y!!][i]!!)) {
                    break
                }
                possibleMoves.add(Pair(i, y!!))
            }
        }
        for (j in (y!! + 1)..7) {
            if (coordsInRange(x!!, j)) {
                if (!checkCellForFreeSpace(cells[j][x!!]!!)) {
                    break
                }
                possibleMoves.add(Pair(x!!, j))
            }
        }
        for (j in (y!! - 1) downTo 0) {
            if (coordsInRange(x!!, j)) {
                if (!checkCellForFreeSpace(cells[j][x!!]!!)) {
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
                if (!checkCellForFreeSpace(cells[j][i]!!)) {
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
                if (!checkCellForFreeSpace(cells[j][i]!!)) {
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
                if (!checkCellForFreeSpace(cells[j][i]!!)) {
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
                if (!checkCellForFreeSpace(cells[j][i]!!)) {
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
            return true
        }
        if (cell.piece!!.color != piece!!.color) {
            return true
        }
        return false
    }
}