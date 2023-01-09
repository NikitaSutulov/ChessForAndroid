package com.nikitasutulov.chessforandroid

import android.app.Activity

class Knight(color: String): Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_knight
            else -> R.drawable.b_knight
        }
    }

    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_knight
    }
}