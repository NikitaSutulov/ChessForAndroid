package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.graphics.drawable.Drawable

class Pawn(color: String): Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_pawn
            else -> R.drawable.b_pawn
        }
    }
    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_pawn
    }
}