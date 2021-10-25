package com.gonzales.mark.n_puzzle

import org.junit.Assert.assertTrue
import org.junit.Test

class ShuffleUtilTest {
    companion object {
        private const val NUM_TEST_CASES = 1000

        private fun isSolvableWithAStar(puzzleStatePair: StatePair): Boolean {
            return SolveUtil.solve(
                puzzleStatePair,
                TestUtil.goalPuzzleState,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            ) != null
        }
    }

    @Test
    fun `Check if shuffling results in solvable 8-puzzles`() {
        for (i in 0 until NUM_TEST_CASES) {
            val puzzleState = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

            val puzzleStatePair: StatePair = ShuffleUtil.getValidShuffledState(
                puzzleState,
                TestUtil.goalPuzzleState,
                TestUtil.BLANK_TILE_MARKER
            )

            assertTrue(isSolvableWithAStar(puzzleStatePair))
        }
    }
}