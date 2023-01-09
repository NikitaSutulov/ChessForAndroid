package com.nikitasutulov.chessforandroid

import android.app.Activity

class Bishop(color: String): Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_bishop
            else -> R.drawable.b_bishop
        }
    }

    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_bishop
    }
}