package com.nikitasutulov.chessforandroid

import android.app.Activity

class Queen(color: String): Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_queen
            else -> R.drawable.b_queen
        }
    }

    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_queen
    }
}