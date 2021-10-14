package com.gonzales.mark.n_puzzle

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class SolveUtil {
    companion object {
        private const val MAX_NUM_CHILDREN = 4
        private const val FRONTIER_INITIAL_CAPACITY = 11
        private val childPositions: ArrayList<ArrayList<Int>> = getChildPositions()

        fun solve(
            puzzleState: ArrayList<Int>,
            blankTilePos: Int,
            goalPuzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): Stack<ArrayList<Int>>? {
            val frontier: PriorityQueue<Node> =
                PriorityQueue(FRONTIER_INITIAL_CAPACITY, NodeComparator())
            val frontierMap: HashMap<Int, Node> = HashMap()
            val explored: HashSet<ArrayList<Int>> = HashSet()

            val startNode = Node(puzzleState, blankTilePos, null, 0, 0)
            frontier.add(startNode)
            frontierMap[startNode.hash()] = startNode

            while (frontier.isNotEmpty()) {
                val node: Node = frontier.poll()!!

                if (isSolved(node.getState(), goalPuzzleState)) {
                    return backtrackPath(node)
                }

                explored.add(node.getState())

                for (child in getChildNodes(node, node.getBlankTilePos())) {
                    val childHash: Int = child.hash()
                    val childInFrontier: Node? = frontierMap[childHash]

                    if (childInFrontier == null && child.getState() !in explored) {
                        frontier.add(child)
                        frontierMap[childHash] = child
                    } else if (childInFrontier != null && childInFrontier.getF() > child.getF()) {
                        frontier.remove(childInFrontier)
                        frontier.add(child)
                        frontierMap[childHash] = child
                    }
                }
            }

            return null
        }

        fun isSolved(puzzleState: ArrayList<Int>, goalPuzzleState: ArrayList<Int>): Boolean {
            return puzzleState == goalPuzzleState
        }

        private fun backtrackPath(node: Node): Stack<ArrayList<Int>> {
            val path: Stack<ArrayList<Int>> = Stack()
            var current: Node? = node

            while (current != null) {
                path.push(current.getState())
                current = current.getParent()
            }

            return path
        }

        private fun getChildNodes(
            node: Node,
            blankTilePos: Int,
        ): ArrayList<Node> {
            val childNodes: ArrayList<Node> = ArrayList(MAX_NUM_CHILDREN)

            for (position in childPositions[blankTilePos]) {
                val childState: ArrayList<Int> = ArrayList(node.getState().size)
                val childBlankTilePos: Int

                for (tile in node.getState()) {
                    childState.add(tile)
                }

                /* Swap the blank tile with the tile in this position. */
                childState[position] = childState[blankTilePos].also {
                    childState[blankTilePos] = childState[position]
                    childBlankTilePos = position
                }

                childNodes.add(Node(childState, childBlankTilePos, node, node.getG() + 1, 0))
            }

            return childNodes
        }

        private fun getChildPositions(): ArrayList<ArrayList<Int>> {
            val childPositions: ArrayList<ArrayList<Int>> = ArrayList()

            /*
             * The two-dimensional illustration of a puzzle grid is shown below:
             *     0 1 2
             *     3 4 5
             *     6 7 8
             */
            childPositions.add(arrayListOf(1, 3))
            childPositions.add(arrayListOf(0, 2, 4))
            childPositions.add(arrayListOf(1, 5))
            childPositions.add(arrayListOf(0, 4, 6))
            childPositions.add(arrayListOf(1, 3, 5, 7))
            childPositions.add(arrayListOf(2, 4, 8))
            childPositions.add(arrayListOf(3, 7))
            childPositions.add(arrayListOf(4, 6, 8))
            childPositions.add(arrayListOf(5, 7))

            return childPositions
        }
    }
}