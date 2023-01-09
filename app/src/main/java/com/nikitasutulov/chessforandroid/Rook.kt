package com.nikitasutulov.chessforandroid

import android.app.Activity

class Rook(color: String): Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_rook
            else -> R.drawable.b_rook
        }
    }

    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_rook
    }
}