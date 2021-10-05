package com.gonzales.mark.n_puzzle

class ShuffleUtil {
    companion object {
        fun getValidShuffledState(
            puzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): Pair<ArrayList<Int>, Int> {
            puzzleState.shuffle()

            if (!isSolvable(puzzleState, blankTileMarker)) {
                swapTiles(puzzleState, blankTileMarker)
            }

            return Pair(puzzleState, puzzleState.indexOf(blankTileMarker))
        }

        private fun countInversions(puzzleState: ArrayList<Int>, blankTileMarker: Int): Int {
            var numInversions: Int = 0

            for (i in 0 until puzzleState.size - 1) {
                for (j in i + 1 until puzzleState.size) {
                    if (!isBlankTile(i, puzzleState, blankTileMarker)
                        && !isBlankTile(j, puzzleState, blankTileMarker)
                        && puzzleState[i] > puzzleState[j]) {
                        numInversions++
                    }
                }
            }

            return numInversions
        }

        private fun isSolvable(puzzleState: ArrayList<Int>, blankTileMarker: Int): Boolean {
            return countInversions(puzzleState, blankTileMarker) % 2 == 0
        }

        private fun swapTiles(puzzleState: ArrayList<Int>, blankTileMarker: Int) {
            var i: Int = 0

            while (isBlankTile(i, puzzleState, blankTileMarker)
                || isBlankTile(i + 1, puzzleState, blankTileMarker)) {
                i++
            }

            /* Swap the flung tile and the blank tile via Kotlin's also idiom. */
            puzzleState[i] = puzzleState[i + 1].also {
                puzzleState[i + 1] = puzzleState[i]
            }
        }

        private fun isBlankTile(tile: Int, puzzleState: ArrayList<Int>, blankTileMarker: Int): Boolean {
            return puzzleState[tile] == blankTileMarker
        }
    }
}