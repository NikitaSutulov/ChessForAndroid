package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.GridLayout

class Board (activity: Activity) {
    val activity = activity
    lateinit var cells: Array<Cell>
    lateinit var gridLayout: GridLayout

    val startMap = arrayOf(
        Rook("BLACK"), Knight("BLACK"), Bishop("BLACK"), Queen("BLACK"), King("BLACK"), Bishop("BLACK"), Knight("BLACK"), Rook("BLACK"),
        Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"), Pawn("BLACK"),
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"), Pawn("WHITE"),
        Rook("WHITE"), Knight("WHITE"), Bishop("WHITE"), Queen("WHITE"), King("WHITE"), Bishop("WHITE"), Knight("WHITE"), Rook("WHITE")
    )

    fun init(gridLayout: GridLayout) {
        this.gridLayout = gridLayout
        val cellsMutableList = mutableListOf<Cell>()
        for (i in 0 until gridLayout.childCount) {
            cellsMutableList.add(Cell(
                gridLayout.getChildAt(i)!! as Button,
                startMap[i],
                this
            ))
            Log.d("POS", i.toString())
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
            Log.d("SHOW", "${i - 1}")
        }
    }
}