package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.graphics.drawable.Drawable

abstract class Piece (color: String) {
    val isMoved = false
    val color = color
    lateinit var icon: Drawable
    fun getPossibleMoves(): Array<Pair<Int, Int>> {
        return arrayOf()
    }
    open fun getDrawableID(activity: Activity): Int {
        return 0
    }
}