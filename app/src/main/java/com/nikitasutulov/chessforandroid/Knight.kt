package com.nikitasutulov.chessforandroid

class Knight(color: String) : Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            Board.WHITE -> R.drawable.w_knight
            else -> R.drawable.b_knight
        }
    }

}