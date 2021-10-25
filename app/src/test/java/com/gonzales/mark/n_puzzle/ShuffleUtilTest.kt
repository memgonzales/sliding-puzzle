package com.gonzales.mark.n_puzzle

import org.junit.Test

class ShuffleUtilTest {
    companion object {
        private fun isSolvableWithAStar(
            puzzleStatePair: StatePair,
            goalPuzzleState: ArrayList<Int>,
            numColumns: Int,
            blankTileMarker: Int
        ): Boolean {
            return SolveUtil.solve(
                puzzleStatePair,
                goalPuzzleState,
                numColumns,
                blankTileMarker
            ) != null
        }
    }

    @Test
    fun `Check if shuffling results in solvable 8-puzzles`() {

    }

    @Test
    fun `Check if solutions to solvable 8-puzzles exist`() {

    }

    @Test
    fun `Check if solutions to unsolvable 8-puzzles do not exist`() {

    }

    @Test
    fun `Check if there is an equal number of solvable and unsolvable 8-puzzles`() {

    }
}