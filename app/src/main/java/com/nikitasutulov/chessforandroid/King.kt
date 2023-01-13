package com.nikitasutulov.chessforandroid

class King(color: String) : Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            Board.WHITE -> R.drawable.w_king
            else -> R.drawable.b_king
        }
    }

}