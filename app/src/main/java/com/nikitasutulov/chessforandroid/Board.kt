package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

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
                button.setOnClickListener {
                    Log.d("Cell click", "You clicked on cell ${getChessCoords(newCell.getX()!!, newCell.getY()!!)}!")
                    Log.d("Cell click", "The piece is ${newCell.piece!!::class.java.simpleName}")
                }
                cellsMutableList.add(mutableListOf())
                cellsMutableList[i].add(newCell)
            }
            cells[i] = cellsMutableList[i].toTypedArray()
        }
    }

    fun show() {
        for (i in 0..7) {
            for (j in 0..7) {
                if (cells[i][j]!!.piece != null) {
                    cells[i][j]!!.button.setBackgroundResource(cells[i][j]!!.piece!!.getDrawableID(activity))
                } else {
                    cells[i][j]!!.button.setBackgroundColor(0x00000000)
                }
            }
        }
    }

    private fun getChessCoords(x: Int, y: Int): String {
        return "${(x.toChar() + 97)}${y + 1}"
    }
}