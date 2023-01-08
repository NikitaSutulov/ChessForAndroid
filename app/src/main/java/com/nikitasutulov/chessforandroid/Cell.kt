package com.nikitasutulov.chessforandroid

import android.util.Log
import android.widget.Button

class Cell(button: Button, piece: Piece?, board: Board) {
    val button = button
    val piece = piece
    val board = board
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
}