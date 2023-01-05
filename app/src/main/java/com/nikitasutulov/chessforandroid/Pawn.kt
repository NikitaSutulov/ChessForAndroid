package com.nikitasutulov.chessforandroid

import android.app.Activity
import android.graphics.drawable.Drawable

class Pawn(color: String): Piece(color) {
    override fun getDrawableID(activity: Activity): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_pawn
            else -> R.drawable.b_pawn
        }
    }
}