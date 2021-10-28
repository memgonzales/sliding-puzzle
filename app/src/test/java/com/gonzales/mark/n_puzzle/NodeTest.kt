package com.gonzales.mark.n_puzzle

import com.gonzales.mark.n_puzzle.TestUtil.Companion.permute
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NodeTest {
    private val parent: Node =
        Node(StatePair(arrayListOf(0, 1, 2, 4, 5, 3, 7, 8, 6), 7), null, 2, 4)

    private val node1: Node =
        Node(StatePair(arrayListOf(0, 1, 2, 4, 5, 3, 7, 8, 6), 7), parent, 1, 4)

    @Test
    fun `Check if the puzzle state hash correctly reflects the puzzle state`() {
        val puzzleState: ArrayList<Int> = node1.puzzleStatePair.puzzleState
        val errorMessage: String = puzzleState.toString()

        assertEquals(errorMessage, 12453786, Node.hashState(puzzleState))
    }

    @Test
    fun `Check if the number of unique puzzle hashes is equal to the total number of puzzle configurations`() {
        val puzzleState: ArrayList<Int> = node1.puzzleStatePair.puzzleState
        val testCases: List<List<Int>> = puzzleState.permute()
        
        val hashes: ArrayList<Int> = ArrayList(testCases.size)
        for (testCase in testCases) {
            hashes.add(Node.hashState(testCase as ArrayList<Int>))
        }

        assertEquals(TestUtil.NUM_PUZZLE_CONFIGS, testCases.distinct().size)
    }

    @Test
    fun `Check if the f-value is computed as the sum of the g- and h-values`() {
        val errorMessage = "\n$node1\n"
        assertEquals(errorMessage, 5, node1.getF())
    }

    @Test
    fun `Check if nodes are equal if their puzzle states are equal`() {
        val node2 =
            Node(StatePair(arrayListOf(0, 1, 2, 4, 5, 3, 7, 8, 6), 7), parent, 2, 7)

        val errorMessage = "\n$node1\n$node2\n"
        assertTrue(errorMessage, node1 == node2)
    }

    @Test
    fun `Check if nodes are unequal if their puzzle states are unequal`() {
        val node2 =
            Node(StatePair(arrayListOf(0, 2, 1, 4, 5, 3, 7, 8, 6), 7), parent, 1, 4)

        val errorMessage = "\n$node1\n$node2\n"
        assertTrue(errorMessage, node1 != node2)
    }

    @Test
    fun `Check if the node hash reflects the puzzle state`() {
        val errorMessage = "\n$node1\n"
        assertEquals(errorMessage, 12453786, node1.hashCode())
    }

    @Test
    fun `Check if the node hash depends only on the puzzle state`() {
        val node2 =
            Node(StatePair(arrayListOf(0, 1, 2, 4, 5, 3, 7, 8, 6), 7), parent, 2, 7)

        val errorMessage = "\n$node1\n$node2\n"
        assertTrue(errorMessage, node1.hashCode() == node2.hashCode())
    }
}