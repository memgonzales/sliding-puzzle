package com.gonzales.mark.n_puzzle

/**
 * Utility class providing methods for shuffling the puzzle tiles.
 *
 * @constructor Creates an object that provides methods for shuffling the puzzle tiles.
 */
class ShuffleUtil {
    /**
     * Companion object containing the methods for shuffling the puzzle tiles.
     */
    companion object {
        /**
         * Returns the puzzle state and the position of the blank tile after a valid shuffling
         * of the puzzle tiles.
         *
         * A shuffling is considered valid if the resulting state is not equivalent to the goal
         * state and if it is solvable (that is, it has an even number of inversions).
         *
         * @param puzzleState Puzzle state (flattened into one dimension, following row-major order).
         * @param goalPuzzleState Goal state (flattened into one dimension, following row-major order).
         * @param blankTileMarker Indicator that the tile is blank.
         * @return Puzzle state (flattened into one dimension, following row-major order) and the
         * position of the blank tile in the puzzle grid (zero-based, following row-major order)
         * after shuffling.
         */
        fun getValidShuffledState(
            puzzleState: ArrayList<Int>,
            goalPuzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): StatePair {
            /* Repeat shuffling if the resulting state is equivalent to the goal state. */
            while (puzzleState == goalPuzzleState) {
                puzzleState.shuffle()

                /*
                 * If the 8-puzzle is not solvable (that is, it has an odd number of inversions),
                 * swap a pair of tiles to change the parity and, thus, guarantee solvability.
                 */
                if (!PuzzleUtil.isSolvable(puzzleState, blankTileMarker)) {
                    PuzzleUtil.swapTiles(puzzleState, blankTileMarker)
                }
            }

            return StatePair(puzzleState, puzzleState.indexOf(blankTileMarker))
        }
    }
}