package com.nikitasutulov.chessforandroid


class Bishop(color: String): Piece(color) {
    override fun getDrawableID(): Int {
        return when (color) {
            Board.WHITE -> R.drawable.w_bishop
            else -> R.drawable.b_bishop
        }
    }

    override fun getHighlightedDrawableID(): Int {
        return R.drawable.g_bishop
    }
}