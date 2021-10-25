package com.gonzales.mark.n_puzzle

class ShuffleUtil {
    companion object {
        fun getValidShuffledState(
            puzzleState: ArrayList<Int>,
            goalPuzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): StatePair {
            /* Repeat shuffling if the resulting state is equivalent to the goal state. */
            while (puzzleState == goalPuzzleState) {
                puzzleState.shuffle()
            }

            /*
            * If the 8-puzzle is not solvable (that is, it has an odd number of inversions),
            * swap a pair of tiles to change the parity and, thus, guarantee solvability.
            */
            if (!PuzzleUtil.isSolvable(puzzleState, blankTileMarker)) {
                PuzzleUtil.swapTiles(puzzleState, blankTileMarker)
            }

            return StatePair(puzzleState, puzzleState.indexOf(blankTileMarker))
        }
    }
}