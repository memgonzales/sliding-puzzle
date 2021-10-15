package com.gonzales.mark.n_puzzle

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.abs

class SolveUtil {
    companion object {
        private const val MAX_NUM_CHILDREN = 4
        private const val FRONTIER_INITIAL_CAPACITY = 11

        private val childPositions: ArrayList<ArrayList<Int>> = getChildPositions()
        private val manhattan: HashMap<Int, Int> = HashMap()

        /**
         * Returns the sequence of states from the current puzzle state to the goal state,
         * as determined using the A* search algorithm with Manhattan distance as the heuristic.
         *
         * A* is an informed search algorithm introduced by Peter E. Hart, Nils J. Nilsson,
         * and Bertram Raphael in the 1968 paper "A Formal Basis for the Heuristic Determination
         * of Minimum Cost Paths," which was published in the <i>IEEE Transactions on System Science
         * and Cybernetics</i> (Volume 4, Issue 2).
         *
         * The implementation of A* in this function is based on the discussion and pseudocode
         * in the third edition of <i>Artificial Intelligence: A Modern Approach</i> by Stuart
         * Russell and Peter Norvig (2010).
         *
         * @param puzzleState Current puzzle state (flattened into one dimension following row-major
         * ordering).
         * @param blankTilePos Position of the blank tile (with respect to the current puzzle state).
         * @param goalPuzzleState Goal state.
         * @param numColumns Number in columns in the puzzle grid.
         * @param blankTileMarker Indicator that the tile is blank.
         * @return Sequence of states from the current puzzle state to the goal state.
         */
        fun solve(
            puzzleState: ArrayList<Int>,
            blankTilePos: Int,
            goalPuzzleState: ArrayList<Int>,
            numColumns: Int,
            blankTileMarker: Int
        ): Stack<ArrayList<Int>>? {
            /*
             * Since the A* algorithm involves partial ordering of the frontier nodes and membership
             * testing, they have to be stored in a data structure that supports both operations
             * efficiently.
             *
             * To satisfy this requirement, two data structures are used:
             *     a) a priority queue (min-heap) for partial ordering
             *     b) a hash map for membership testing
             *
             * The decision to use a hash map instead of a hash set is motivated by the step wherein
             * the f-value of the current child node is compared to the f-value of the frontier node
             * that has the same state (should it exist). This necessitates the retrieval of the
             * f-value of the latter, which is an operation not natively supported by a hash set.
             */
            val frontier: PriorityQueue<Node> =
                PriorityQueue(FRONTIER_INITIAL_CAPACITY, NodeComparator())
            val frontierMap: HashMap<Int, Node> = HashMap()
            val explored: HashSet<ArrayList<Int>> = HashSet()

            val startNode = Node(
                puzzleState,
                blankTilePos,
                null,
                0,
                getManhattan(puzzleState, numColumns, blankTileMarker)
            )

            frontier.add(startNode)
            frontierMap[startNode.hash()] = startNode

            while (frontier.isNotEmpty()) {
                val node: Node = frontier.poll()!!

                if (isSolved(node.getState(), goalPuzzleState)) {
                    return backtrackPath(node)
                }

                explored.add(node.getState())

                val childNodes: ArrayList<Node> = getChildNodes(
                    node,
                    node.getBlankTilePos(),
                    numColumns,
                    blankTileMarker
                )

                for (child in childNodes) {
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
            numColumns: Int,
            blankTileMarker: Int
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

                childNodes.add(
                    Node(
                        childState,
                        childBlankTilePos,
                        node,
                        node.getG() + 1,
                        getManhattan(childState, numColumns, blankTileMarker)
                    )
                )
            }

            return childNodes
        }

        private fun getChildPositions(): ArrayList<ArrayList<Int>> {
            /*
             * The two-dimensional illustration of a puzzle grid is shown below:
             *     0 1 2
             *     3 4 5
             *     6 7 8
             */
            return arrayListOf(
                arrayListOf(1, 3),
                arrayListOf(0, 2, 4),
                arrayListOf(1, 5),
                arrayListOf(0, 4, 6),
                arrayListOf(1, 3, 5, 7),
                arrayListOf(2, 4, 8),
                arrayListOf(3, 7),
                arrayListOf(4, 6, 8),
                arrayListOf(5, 7)
            )
        }

        private fun getManhattan(
            puzzleState: ArrayList<Int>,
            numColumns: Int,
            blankTileMarker: Int
        ): Int {
            val hash: Int = Node.hashState(puzzleState)

            if (manhattan[hash] != null) {
                return manhattan[hash]!!
            }

            var sumManhattan = 0
            for (i in 0 until puzzleState.size) {
                if (puzzleState[i] != blankTileMarker) {
                    sumManhattan +=
                        abs(i / numColumns - puzzleState[i] / numColumns) +
                                abs(i % numColumns - puzzleState[i] % numColumns)
                }
            }

            manhattan[hash] = sumManhattan

            return sumManhattan
        }
    }
}