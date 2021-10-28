package com.gonzales.mark.n_puzzle

import org.junit.Assert.assertEquals
import org.junit.Test

class NodeByFTest {
    private val puzzleStatePair: StatePair = StatePair(arrayListOf(0, 1, 2, 4, 5, 3, 7, 8, 6), 7)
    private val parent: Node =
        Node(StatePair(arrayListOf(0, 1, 2, 4, 5, 3, 7, 8, 6), 7), null, 1, 4)

    private val comparator: NodeByF = NodeByF()

    @Test
    fun `Check if nodes are equal if their f-values and puzzle state pairs are equal`() {
        val node1 = Node(puzzleStatePair, parent, 1, 5)
        val node2 = Node(puzzleStatePair, parent, 2, 4)

        val errorMessage = "\n$node1\n$node2\n"

        assertEquals(errorMessage, 0, comparator.compare(node1, node2))
    }

    @Test
    fun `Check if nodes are equal if their f-values are equal but their puzzle states are not`() {
        val node1 = Node(puzzleStatePair, parent, 1, 5)
        val node2 = Node(parent.puzzleStatePair, null, 2, 4)

        val errorMessage = "\n$node1\n$node2\n"

        assertEquals(errorMessage, 0, comparator.compare(node1, node2))
    }

    @Test
    fun `Check if the first node is less than the second node if the f-value of the former is less than that of the latter`() {
        val node1 = Node(puzzleStatePair, parent, 1, 2)
        val node2 = Node(puzzleStatePair, parent, 2, 3)

        val errorMessage = "\n$node1\n$node2\n"

        assertEquals(errorMessage, -1, comparator.compare(node1, node2))
    }

    @Test
    fun `Check if the first node is greater than the second node if the f-value of the former is greater than that of the latter`() {
        val node1 = Node(puzzleStatePair, parent, 3, 5)
        val node2 = Node(puzzleStatePair, parent, 2, 3)

        val errorMessage = "\n$node1\n$node2\n"

        assertEquals(errorMessage, 1, comparator.compare(node1, node2))
    }
}