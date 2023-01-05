package com.nikitasutulov.chessforandroid

import android.app.Activity

class King(color: String): Piece(color) {
    override fun getDrawableID(activity: Activity): Int {
        return when (color) {
            "WHITE" -> R.drawable.w_king
            else -> R.drawable.b_king
        }
    }
}