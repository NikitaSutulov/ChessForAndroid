package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.graphics.drawable.Drawable

abstract class Piece (color: String) {
    val isMoved = false
    val color = color
    lateinit var icon: Drawable

    open fun getDrawableID(): Int {
        return 0
    }

    open fun getHighlightedDrawableID(): Int {
        return 0
    }
}