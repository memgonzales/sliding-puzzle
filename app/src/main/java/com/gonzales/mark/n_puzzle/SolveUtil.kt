package com.gonzales.mark.n_puzzle

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class SolveUtil {
    companion object {
        private const val MAX_NUM_NEIGHBORS = 4
        private val neighborPositions: ArrayList<ArrayList<Int>> = getNeighborPositions()

        fun solve(
            puzzleState: ArrayList<Int>,
            blankTilePos: Int,
            goalPuzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): ArrayList<ArrayList<Int>>? {
            val frontierQueue: PriorityQueue<Node> = PriorityQueue()
            val frontierSet: HashSet<Node> = HashSet()
            val exploredSet: HashSet<ArrayList<Int>> = HashSet()
            val cameFrom: HashMap<ArrayList<Int>, ArrayList<Int>> = HashMap()

            val startNode = Node(puzzleState, 0, 0)
            frontierQueue.add(startNode)
            frontierSet.add(startNode)

            while (frontierQueue.isNotEmpty()) {
                val currentNode: Node = frontierQueue.poll()!!
                frontierSet.remove(currentNode)

                if (isSolved(currentNode.getState(), goalPuzzleState)) {
                    return null
                }

                exploredSet.add(currentNode.getState())


            }

            return null
        }

        fun isSolved(puzzleState: ArrayList<Int>, goalPuzzleState: ArrayList<Int>): Boolean {
            return puzzleState == goalPuzzleState
        }

        private fun getNeighborNodes(
            node: Node,
            blankTilePos: Int,
        ): ArrayList<Node> {
            val neighborNodes: ArrayList<Node> = ArrayList(MAX_NUM_NEIGHBORS)

            for (position in neighborPositions[blankTilePos]) {
                val neighborState: ArrayList<Int> = ArrayList(node.getState().size)
                val neighborBlankTilePos: Int

                for (tile in node.getState()) {
                    neighborState.add(tile)
                }

                /* Swap the blank tile with the tile in this position. */
                neighborState[position] = neighborState[blankTilePos].also {
                    neighborState[blankTilePos] = neighborState[position]
                    neighborBlankTilePos = position
                }

                neighborNodes.add(Node(neighborState, neighborBlankTilePos, node.getG() + 1, 0))
            }

            return neighborNodes
        }

        private fun getNeighborPositions(): ArrayList<ArrayList<Int>> {
            val neighbors: ArrayList<ArrayList<Int>> = ArrayList()

            /*
             * The two-dimensional illustration of a puzzle grid is shown below:
             *     0 1 2
             *     3 4 5
             *     6 7 8
             */
            neighbors.add(arrayListOf(1, 3))
            neighbors.add(arrayListOf(0, 2, 4))
            neighbors.add(arrayListOf(1, 5))
            neighbors.add(arrayListOf(0, 4, 6))
            neighbors.add(arrayListOf(1, 3, 5, 7))
            neighbors.add(arrayListOf(2, 4, 8))
            neighbors.add(arrayListOf(3, 7))
            neighbors.add(arrayListOf(4, 6, 8))
            neighbors.add(arrayListOf(5, 7))

            return neighbors
        }
    }
}