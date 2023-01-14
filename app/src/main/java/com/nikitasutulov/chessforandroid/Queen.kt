package com.nikitasutulov.chessforandroid

class Queen(color: String) : Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            Board.WHITE -> R.drawable.w_queen
            else -> R.drawable.b_queen
        }
    }
}