package com.gonzales.mark.n_puzzle

import java.util.*
import java.util.Collections.swap
import kotlin.math.abs

/**
 * Utility class providing methods related to the puzzle state.
 *
 * @constructor Creates an object that provides methods related to the puzzle state.
 */
class PuzzleUtil {
    /**
     * Companion object containing the methods related to the puzzle state.
     */
    companion object {
        /**
         * Maps a state (technically, its hash) to its Manhattan distance vis-à-vis the goal state.
         */
        private val manhattan: HashMap<Int, Int> = HashMap()

        /**
         * Checks if the shuffled puzzle grid is solvable.
         *
         * A puzzle grid is solvable if and only if the number of inversions in the puzzle state is even.
         *
         * @param puzzleState Current puzzle state (flattened into one dimension, following row-major order).
         * @param blankTileMarker Indicator that the tile is blank.
         * @return <code>true</code> if the shuffled puzzle grid is solvable; <code>false</code>,
         * otherwise.
         */
        fun isSolvable(puzzleState: ArrayList<Int>, blankTileMarker: Int): Boolean {
            return countInversions(puzzleState, blankTileMarker) % 2 == 0
        }

        /**
         * Counts the number of inversions in the puzzle state.
         *
         * An inversion refers to a pair of elements that are out of their natural order
         * (in this 8-puzzle, the natural order of the elements/positions is ascending).
         *
         * @param puzzleState Current puzzle state (flattened into one dimension, following row-major order).
         * @param blankTileMarker Indicator that the tile is blank.
         * @return Number of inversions in the puzzle state.
         */
        private fun countInversions(puzzleState: ArrayList<Int>, blankTileMarker: Int): Int {
            var numInversions = 0

            for (i in 0 until puzzleState.size - 1) {
                for (j in i + 1 until puzzleState.size) {
                    /* Blank tiles are not considered when counting the number of inversions. */
                    if (!isBlankTile(i, puzzleState, blankTileMarker)
                        && !isBlankTile(j, puzzleState, blankTileMarker)
                        && puzzleState[i] > puzzleState[j]
                    ) {
                        numInversions++
                    }
                }
            }

            return numInversions
        }

        /**
         * Swaps a pair of tiles.
         *
         * This method is intended to be used for changing the parity of the number of inversions
         * if the puzzle state is initially unsolvable, in effect turning it to a solvable puzzle.
         *
         * @param puzzleState Current puzzle state (flattened into one dimension, following row-major order).
         * @param blankTileMarker Indicator that the tile is blank.
         */
        fun swapTiles(puzzleState: ArrayList<Int>, blankTileMarker: Int) {
            var position = 0
            while (isBlankTile(position, puzzleState, blankTileMarker)
                || isBlankTile(position + 1, puzzleState, blankTileMarker)
            ) {
                position++
            }

            swap(puzzleState, position, position + 1)
        }

        /**
         * Checks if the tile specified by the given position is a blank tile.
         *
         * @param position Position of the tile in the puzzle grid (zero-based, following row-major order).
         * @param puzzleState Current puzzle state (flattened into one dimension, following row-major order).
         * @param blankTileMarker Indicator that the tile is blank.
         * @return <code>true</code> if the tile specified by the given position is a blank tile;
         * <code>false</code>, otherwise.
         */
        private fun isBlankTile(
            position: Int,
            puzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): Boolean {
            return puzzleState[position] == blankTileMarker
        }

        /**
         * Computes the Manhattan distance of the given state vis-à-vis the goal state.
         *
         * This value is computed as the sum of the Manhattan (straight-line) distances of the
         * positions in the given state vis-à-vis the positions in the goal state.
         *
         * @param puzzleState Current puzzle state (flattened into one dimension, following row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @param blankTileMarker Indicator that the tile is blank.
         * @return Manhattan distance of the given state vis-à-vis the goal state.
         */
        fun getManhattan(
            puzzleState: ArrayList<Int>,
            numColumns: Int,
            blankTileMarker: Int
        ): Int {
            val hash: Int = Node.hashState(puzzleState)

            /*
             * If the Manhattan distance is already stored in the hash map, there is no need to
             * recompute its value.
             */
            if (manhattan[hash] != null) {
                return manhattan[hash]!!
            }

            /*
             * Compute the sum of the Manhattan distances of the positions in the given state
             * vis-à-vis the positions in the goal state.
             */
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