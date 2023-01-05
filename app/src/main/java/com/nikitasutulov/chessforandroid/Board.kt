package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.GridLayout

class Board (activity: Activity) {
    val activity = activity
    lateinit var cells: Array<Cell>
    lateinit var gridLayout: GridLayout

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

    fun init(gridLayout: GridLayout) {
        this.gridLayout = gridLayout
        val cellsMutableList = mutableListOf<Cell>()
        for (i in 0 until gridLayout.childCount) {
            cellsMutableList.add(Cell(
                gridLayout.getChildAt(i)!! as Button,
                startMap[i/8][i%8],
                this
            ))
        }
        cells = cellsMutableList.toTypedArray()
    }

    fun show() {
        for (i in 0 until gridLayout.childCount) {
            if (cells[i].piece != null) {
                cells[i].button.setBackgroundResource(cells[i].piece!!.getDrawableID(activity))
            } else {
                cells[i].button.setBackgroundColor(0x00000000)
            }
        }
    }
}