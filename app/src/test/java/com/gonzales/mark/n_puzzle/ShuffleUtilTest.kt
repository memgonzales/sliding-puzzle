package com.gonzales.mark.n_puzzle

import com.gonzales.mark.n_puzzle.TestUtil.Companion.permute
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShuffleUtilTest {
    companion object {
        private const val NUM_TEST_CASES = 1000
        private val NUM_PUZZLE_CONFIGS = countPuzzleConfigs()

        private fun countPuzzleConfigs(): Int {
            var numPuzzleConfigs = 1
            for (i in 1..TestUtil.NUM_TILES) {
                numPuzzleConfigs *= i
            }

            return numPuzzleConfigs
        }

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

    @Test
    fun `Check if the number of solvable 8-puzzles is equal to half of 9!`() {
        val puzzleState = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        val testCases: List<List<Int>> = puzzleState.permute()

        var numSolvable = 0
        for (testCase in testCases) {
            if (ShuffleUtil.isSolvable(testCase as ArrayList<Int>, TestUtil.BLANK_TILE_MARKER)) {
                numSolvable++
            }
        }

        assertEquals(NUM_PUZZLE_CONFIGS / 2, numSolvable)
    }

    @Test
    fun `Check if the number of unsolvable 8-puzzles is equal to half of 9!`() {
        val puzzleState = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        val testCases: List<List<Int>> = puzzleState.permute()

        var numUnsolvable = 0
        for (testCase in testCases) {
            if (!ShuffleUtil.isSolvable(testCase as ArrayList<Int>, TestUtil.BLANK_TILE_MARKER)) {
                numUnsolvable++
            }
        }

        assertEquals(NUM_PUZZLE_CONFIGS / 2, numUnsolvable)
    }
}