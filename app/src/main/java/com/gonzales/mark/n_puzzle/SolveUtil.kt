package com.gonzales.mark.n_puzzle

import java.util.*
import java.util.Collections.swap
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

/**
 * Utility class providing constants and methods related to solving an 8-puzzle.
 *
 * @constructor Creates an object that provides constants and methods related to solving an 8-puzzle.
 */
class SolveUtil {
    /**
     * Companion object containing constants and methods related to solving an 8-puzzle.
     */
    companion object {
        /**
         * Maximum number of children of a node, that is, the maximum number of states than can be
         * reached from a given state after exactly a single move.
         *
         * Following the mechanics of an 8-puzzle, this is equal to 4, corresponding to the four
         * cardinal directions.
         */
        private const val MAX_NUM_CHILDREN = 4

        /**
         * Initial capacity of the priority queue storing the frontier nodes.
         *
         * Since the partial ordering of the elements in this priority queue relies on a custom
         * comparator, its constructor is as follows:
         * <code>PriorityQueue(int initialCapacity, Comparator<? super E> comparator)</code>.
         *
         * Technically, the given initial capacity is a dummy value meant only to supply the
         * required arguments of this constructor.
         */
        private const val FRONTIER_INITIAL_CAPACITY = 11

        /**
         * Stores the positions adjacent to each position in the puzzle grid.
         */
        private val childPositions: ArrayList<ArrayList<Int>> = getChildPositions()

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
         * Note that, although the position of the blank tile can be inferred from the puzzle state,
         * it is passed as a parameter in the interest of efficiency (that is, to avoid linear
         * searching).
         *
         * @param puzzleStatePair Current puzzle state (flattened into one dimension, following
         * row-major order) and the position of the blank tile in the puzzle grid (zero-based,
         * following row-major order).
         * @param goalPuzzleState Goal state.
         * @param numColumns Number in columns in the puzzle grid.
         * @param blankTileMarker Indicator that the tile is blank.
         * @return Sequence of states from the current puzzle state to the goal state.
         */
        fun solve(
            puzzleStatePair: StatePair,
            goalPuzzleState: ArrayList<Int>,
            numColumns: Int,
            blankTileMarker: Int
        ): Stack<StatePair>? {
            val puzzleState: ArrayList<Int> = puzzleStatePair.puzzleState
            val blankTilePos: Int = puzzleStatePair.blankTilePos

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
                PriorityQueue(FRONTIER_INITIAL_CAPACITY, NodeByF())
            val frontierMap: HashMap<Int, Node> = HashMap()

            /*
             * To prevent expanding already-explored nodes, keep track of them. Use a hash set for
             * efficient membership testing.
             */
            val explored: HashSet<ArrayList<Int>> = HashSet()

            /*
             * Set the parent of the start node to null and its g-value (path cost) to 0 since
             * its depth is 0.
             */
            val startNode = Node(
                StatePair(puzzleState, blankTilePos),
                null,
                0,
                PuzzleUtil.getManhattan(puzzleState, numColumns, blankTileMarker)
            )

            /* Add the start node to the frontier. */
            frontier.add(startNode)
            frontierMap[startNode.hashCode()] = startNode

            /*
             * If there are no more frontier nodes but the solution has not yet been found,
             * then the puzzle is unsolvable.
             */
            while (frontier.isNotEmpty()) {
                /* Consider the node with the least f-value. */
                val node: Node = frontier.poll()!!

                if (isSolved(node.puzzleStatePair.puzzleState, goalPuzzleState)) {
                    return backtrackPath(node)
                }

                /* Expand the current node, and add it to the list of explored nodes. */
                explored.add(node.puzzleStatePair.puzzleState)

                val childNodes: ArrayList<Node> = getChildNodes(
                    node,
                    node.puzzleStatePair.blankTilePos,
                    numColumns,
                    blankTileMarker
                )

                for (child in childNodes) {
                    /* Store the hash into a variable for efficiency. */
                    val childHash: Int = child.hashCode()
                    val childInFrontier: Node? = frontierMap[childHash]

                    /*
                     * Add the node to the list of frontier nodes if it is neither in this list nor
                     * in the list of already-explored nodes.
                     */
                    if (childInFrontier == null && child.puzzleStatePair.puzzleState !in explored) {
                        frontier.add(child)
                        frontierMap[childHash] = child
                    } else if (childInFrontier != null && childInFrontier.getF() > child.getF()) {
                        /*
                         * If a node with the same state is found in the list of frontier nodes
                         * but with higher f-value, replace this frontier node with the child node,
                         * as it represents a better (cheaper) path to the goal state.
                         */
                        frontier.remove(childInFrontier)
                        frontier.add(child)
                        frontierMap[childHash] = child
                    }
                }
            }

            /*
             * Since the 8-puzzle grids generated in this app are guaranteed to be solvable
             * (via the getValidShuffledState() method in ShuffleUtil), this code should be
             * unreachable.
             */
            return null
        }

        /**
         * Checks if the current puzzle state is equal to the goal state.
         *
         * @param puzzleState Puzzle state (flattened into one dimension, following row-major order).
         * @param goalPuzzleState Goal state (flattened into one dimension, following row-major order).
         * @return <code>true</code> if the current puzzle state is equal to the goal state;
         * <code>false</code>, otherwise.
         */
        fun isSolved(puzzleState: ArrayList<Int>, goalPuzzleState: ArrayList<Int>): Boolean {
            return puzzleState == goalPuzzleState
        }

        /**
         * Traces the path from the specified node back to the start node.
         *
         * @param node Current node.
         * @return Path from the specified node back to the start node, with the start node at the
         * top of the stack.
         */
        private fun backtrackPath(node: Node): Stack<StatePair> {
            val path: Stack<StatePair> = Stack()
            var current: Node? = node

            while (current != null) {
                path.push(current.puzzleStatePair)
                current = current.parent
            }

            return path
        }

        /**
         * Gets the child nodes of the given node.
         *
         * The positions pertained to by these child nodes are adjacent to the position pertained to
         * by the given node.
         *
         * @param node Current node.
         * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
         * row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @param blankTileMarker Indicator that the tile is blank.
         * @return Child nodes of the given node.
         */
        private fun getChildNodes(
            node: Node,
            blankTilePos: Int,
            numColumns: Int,
            blankTileMarker: Int
        ): ArrayList<Node> {
            val childNodes: ArrayList<Node> = ArrayList(MAX_NUM_CHILDREN)

            for (position in childPositions[blankTilePos]) {
                val childState: ArrayList<Int> = ArrayList(node.puzzleStatePair.puzzleState.size)

                for (tile in node.puzzleStatePair.puzzleState) {
                    childState.add(tile)
                }

                swap(childState, position, blankTilePos)

                /* The depth (g-value) of a child node is one greater than that of the current node. */
                childNodes.add(
                    Node(
                        StatePair(childState, position),
                        node,
                        node.g + 1,
                        PuzzleUtil.getManhattan(childState, numColumns, blankTileMarker)
                    )
                )
            }

            return childNodes
        }

        /**
         * Gets the positions adjacent to each position in the puzzle grid.
         *
         * @return Positions adjacent to each position in the puzzle grid, with the first element
         * of the returned <code>ArrayList</code> pertaining to the positions adjacent to 0, the
         * second element pertaining to those adjacent to 1, and so on.
         */
        private fun getChildPositions(): ArrayList<ArrayList<Int>> {
            /*
             * The two-dimensional illustration of a puzzle grid is shown below:
             *     0 1 2
             *     3 4 5
             *     6 7 8
             *
             * The first element in this array list refers to the child positions of 0;
             * the second elements, to those of 1; and so on.
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
    }
}