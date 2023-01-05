package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.widget.Button
import android.widget.GridLayout

class Board (activity: Activity) {
    val activity = activity
    lateinit var cells: Array<Cell>
    fun init(gridLayout: GridLayout) {
        val cellsMutableList = mutableListOf<Cell>()
        for (i in 1..8) {
            for (j in 1..8) {
                cellsMutableList.add(Cell(
                    gridLayout.getChildAt((i - 1) * 8 + j - 1)!! as Button,
                    getStartingPieceFromCoords(i, j),
                    this
                ))
            }
        }
        cells = cellsMutableList.toTypedArray()
    }

    private fun getStartingPieceFromCoords(x: Int, y: Int): Piece? {
        var color: String = "WHITE"
        var piece: Piece? = null

        when (y) {
            1 -> color = "WHITE"
            2 -> piece = Pawn("WHITE")
            7 -> piece = Pawn("BLACK")
            8 -> color = "BLACK"
            else -> piece = null
        }

        when (x) {
            1, 8 -> piece = Rook(color)
            2, 7 -> piece = Knight(color)
            3, 6 -> piece = Bishop(color)
            4    -> piece = Queen(color)
            5    -> piece = King(color)
        }
        return piece
    }

    fun show() {
        for (i in 1..8) {
            if (cells[i-1] != null) {
                cells[i-1].button.setBackgroundResource(cells[i-1].piece!!.getDrawableID(activity))
            }
        }
    }
}