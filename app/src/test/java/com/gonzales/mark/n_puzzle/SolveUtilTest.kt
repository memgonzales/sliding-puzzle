package com.gonzales.mark.n_puzzle

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class SolveUtilTest {
    @Test
    fun `Check if the sequence of moves returned for solvable puzzles obeys the tile sliding rules`() {
        val puzzleState: ArrayList<Int> = arrayListOf(0, 1, 2, 8, 3, 5, 6, 4, 7)

        val expectedSolution: Stack<StatePair> = Stack()
        expectedSolution.push(StatePair(arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8), 8))
        expectedSolution.push(StatePair(arrayListOf(0, 1, 2, 3, 4, 5, 6, 8, 7), 7))
        expectedSolution.push(StatePair(arrayListOf(0, 1, 2, 3, 8, 5, 6, 4, 7), 4))
        expectedSolution.push(StatePair(arrayListOf(0, 1, 2, 8, 3, 5, 6, 4, 7), 3))

        val actualSolution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertEquals(errorMessage, expectedSolution, actualSolution)
    }

    @Test
    fun `Check if no sequence of moves is returned for unsolvable puzzles`() {
        val puzzleState: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 5, 7, 6, 8)
        val solution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertTrue(errorMessage, solution == null)
    }

    @Test
    fun `Check if the goal state is reached in 0 moves if the current state is the goal state itself`() {
        val puzzleState: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        val solution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertEquals(errorMessage, 0, solution?.size?.minus(1))
    }

    @Test
    fun `Check if the goal state is reached in 1 move if the current state is reached by sliding the tile beside the blank tile to the right`() {
        val puzzleState: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 5, 6, 8, 7)
        val solution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertEquals(errorMessage, 1, solution?.size?.minus(1))
    }

    @Test
    fun `Check if the goal state is reached in 1 move if the current state is reached by sliding the tile above the blank tile down`() {
        val puzzleState: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 8, 6, 7, 5)
        val solution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertEquals(errorMessage, 1, solution?.size?.minus(1))
    }

    /**
     * The current state tested in this method is derived from the configuration noted by Alexander Reinefeld
     * in the 1993 paper "Complete Solution of the Eight-Puzzle and the Benefit of Node Ordering in IDA,"
     * which was presented at the 13<sup>th</sup> International Joint Conference on Artificial Intelligence
     * and published in its proceedings.
     */
    @Test
    fun `Check if the goal state is reached in 31 moves if the current state is one of the two states that require the most number of moves`() {
        val puzzleState: ArrayList<Int> = arrayListOf(7, 5, 6, 1, 4, 3, 2, 8, 0)
        val solution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertEquals(errorMessage, 31, solution?.size?.minus(1))
    }

    /**
     * The current state tested in this method is derived from the configuration noted by Alexander Reinefeld
     * in the 1993 paper "Complete Solution of the Eight-Puzzle and the Benefit of Node Ordering in IDA,"
     * which was presented at the 13<sup>th</sup> International Joint Conference on Artificial Intelligence
     * and published in its proceedings.
     */
    @Test
    fun `Check if the goal state is reached in 31 moves if the current state is the other of the two states that require the most number of moves`() {
        val puzzleState: ArrayList<Int> = arrayListOf(5, 3, 6, 7, 4, 8, 2, 1, 0)
        val solution: Stack<StatePair>? =
            SolveUtil.solve(
                StatePair(puzzleState, puzzleState.indexOf(TestUtil.BLANK_TILE_MARKER)),
                TestUtil.GOAL_PUZZLE_STATE,
                TestUtil.NUM_COLUMNS,
                TestUtil.BLANK_TILE_MARKER
            )

        val errorMessage = "\n${TestUtil.displayGrid(puzzleState)}\n"

        assertEquals(errorMessage, 31, solution?.size?.minus(1))
    }
}