package com.nikitasutulov.chessforandroid

class Pawn(color: String) : Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            Board.WHITE -> R.drawable.w_pawn
            else -> R.drawable.b_pawn
        }
    }
    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_pawn
    }
}