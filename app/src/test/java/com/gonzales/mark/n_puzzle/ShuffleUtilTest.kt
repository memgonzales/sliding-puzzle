package com.gonzales.mark.n_puzzle

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ShuffleUtilTest {
    companion object {
        private val NUM_TESTS = 2 * TestUtil.NUM_PUZZLE_CONFIGS
    }

    @Test
    fun `Check if shuffling results in solvable 8-puzzles`() {
        for (i in 0 until NUM_TESTS) {
            val puzzleState = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

            val puzzleStatePair: StatePair = ShuffleUtil.getValidShuffledState(
                puzzleState,
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.BLANK_TILE_MARKER
            )

            val errorMessage = "\n${TestUtil.displayGrid(puzzleStatePair.puzzleState)}\n"

            assertTrue(
                errorMessage,
                PuzzleUtil.isSolvable(
                    puzzleStatePair.puzzleState,
                    TestUtil.BLANK_TILE_MARKER
                )
            )
        }
    }

    @Test
    fun `Check if shuffling does not result in an already-solved 8-puzzle`() {
        for (i in 0 until NUM_TESTS) {
            val puzzleState = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

            val puzzleStatePair: StatePair = ShuffleUtil.getValidShuffledState(
                puzzleState,
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.BLANK_TILE_MARKER
            )

            assertFalse(puzzleStatePair.puzzleState == TestUtil.GOAL_PUZZLE_STATE)
        }
    }
}