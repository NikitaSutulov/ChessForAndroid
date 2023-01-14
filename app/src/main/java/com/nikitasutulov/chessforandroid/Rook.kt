package com.nikitasutulov.chessforandroid

class Rook(color: String) : Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            Board.WHITE -> R.drawable.w_rook
            else -> R.drawable.b_rook
        }
    }
}