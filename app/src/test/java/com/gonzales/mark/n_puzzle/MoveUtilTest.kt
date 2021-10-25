package com.gonzales.mark.n_puzzle

import org.junit.Assert.assertTrue
import org.junit.Test

class MoveUtilTest {
    companion object {
        /**
         * Number of columns in the 8-puzzle grid.
         */
        private const val NUM_COLUMNS = 3

        /**
         * Number of tiles in the 8-puzzle grid.
         */
        private const val NUM_TILES = NUM_COLUMNS * NUM_COLUMNS

        /**
         * Indicator that the tile is blank.
         */
        private const val BLANK_TILE_MARKER = NUM_TILES - 1
    }

    private fun generateTestCases(): ArrayList<ArrayList<Int>> {
        val testCases: ArrayList<ArrayList<Int>> = ArrayList(NUM_TILES)

        for (position in 0 until NUM_TILES) {
            val testCase: ArrayList<Int> = ArrayList(NUM_TILES)
            for (tile in 0 until NUM_TILES) {
                testCase.add(tile)
            }

            val blankTilePos: Int = testCase.indexOf(BLANK_TILE_MARKER)
            testCase[position] = testCase[blankTilePos].also {
                testCase[blankTilePos] = testCase[position]
            }

            testCases.add(testCase)
        }

        return testCases
    }

    @Test
    fun `Test cases generated should cover all possible positions of the blank tile`() {
        val testCases: ArrayList<ArrayList<Int>> = generateTestCases()
        val expectedPositionSet: HashSet<Int> = HashSet()
        val actualPositionSet: HashSet<Int> = HashSet()

        for (position in 0 until NUM_TILES) {
            expectedPositionSet.add(position)
        }

        for (testCase in testCases) {
            actualPositionSet.add(testCase.indexOf(BLANK_TILE_MARKER))
        }

        assertTrue(expectedPositionSet == actualPositionSet)
    }
}