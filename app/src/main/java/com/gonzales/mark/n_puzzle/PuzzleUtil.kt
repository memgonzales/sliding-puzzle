package com.gonzales.mark.n_puzzle

import java.util.Collections.swap

class PuzzleUtil {
    companion object {
        fun isSolvable(puzzleState: ArrayList<Int>, blankTileMarker: Int): Boolean {
            return countInversions(puzzleState, blankTileMarker) % 2 == 0
        }

        private fun countInversions(puzzleState: ArrayList<Int>, blankTileMarker: Int): Int {
            var numInversions = 0

            for (i in 0 until puzzleState.size - 1) {
                for (j in i + 1 until puzzleState.size) {
                    if (!isBlankTile(i, puzzleState, blankTileMarker)
                        && !isBlankTile(j, puzzleState, blankTileMarker)
                        && puzzleState[i] > puzzleState[j]
                    ) {
                        numInversions++
                    }
                }
            }

            return numInversions
        }

        fun swapTiles(puzzleState: ArrayList<Int>, blankTileMarker: Int) {
            var position = 0
            while (isBlankTile(position, puzzleState, blankTileMarker)
                || isBlankTile(position + 1, puzzleState, blankTileMarker)
            ) {
                position++
            }

            swap(puzzleState, position, position + 1)
        }

        /**
         * Checks if the tile specified by the given position is a blank tile.
         *
         * @param position Position of the tile in the puzzle grid (zero-based, following row-major order).
         * @param puzzleState Current puzzle state (flattened into one dimension, following row-major order).
         * @param blankTileMarker Indicator that the tile is blank.
         * @return <code>true</code> if the tile specified by the given position is a blank tile;
         * <code>false</code>, otherwise.
         */
        private fun isBlankTile(
            position: Int,
            puzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): Boolean {
            return puzzleState[position] == blankTileMarker
        }
    }
}