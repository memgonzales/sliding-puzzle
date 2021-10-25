package com.gonzales.mark.n_puzzle

class PuzzleConstant {
    companion object {
        /**
         * Number of columns in the 8-puzzle grid.
         */
        const val NUM_COLUMNS = 3

        /**
         * Number of tiles in the 8-puzzle grid.
         */
        const val NUM_TILES = NUM_COLUMNS * NUM_COLUMNS

        /**
         * Indicator that the tile is blank.
         */
        const val BLANK_TILE_MARKER = NUM_TILES - 1
    }
}