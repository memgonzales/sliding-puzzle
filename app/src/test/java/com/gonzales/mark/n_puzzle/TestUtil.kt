package com.gonzales.mark.n_puzzle

import java.util.*

class TestUtil {
    companion object {
        /**
         * Number of columns in the 8-puzzle grid.
         */
        const val NUM_COLUMNS = 3

        /**
         * Number of tiles in the 8-puzzle grid.
         */
        const val NUM_TILES = NUM_COLUMNS * NUM_COLUMNS

        /**
         * Indicator that the tile is blank.
         */
        const val BLANK_TILE_MARKER = NUM_TILES - 1

        val NUM_PUZZLE_CONFIGS = countPuzzleConfigs()

        val GOAL_PUZZLE_STATE: ArrayList<Int> = initGoalPuzzleState()

        private fun countPuzzleConfigs(): Int {
            var numPuzzleConfigs = 1
            for (i in 1..NUM_TILES) {
                numPuzzleConfigs *= i
            }

            return numPuzzleConfigs
        }

        private fun initGoalPuzzleState(): ArrayList<Int> {
            val goalPuzzleState: ArrayList<Int> = ArrayList(NUM_TILES)
            for (tile in 0 until NUM_TILES) {
                goalPuzzleState.add(tile)
            }

            return goalPuzzleState
        }

        fun displayGrid(puzzleState: ArrayList<Int>): String {
            var grid = ""
            var i = 1

            for (tile in puzzleState) {
                grid += if (tile == BLANK_TILE_MARKER) {
                    "- "
                } else {
                    "$tile "
                }

                if (i % NUM_COLUMNS == 0) {
                    grid += "\n"
                }

                i++
            }

            return grid
        }

        /**
         * Generates all the permutations of this list using Heap's algorithm.
         *
         * Heap's algorithm was introduced by B.R. Heap in the 1963 paper "Permutations by Interchanges,"
         * which was published in <i>The Computer Journal</i> (Volume 6, Issue 3). This method follows
         * a recursive implementation of the said algorithm.
         *
         * @return List of all the permutations of this list.
         */
        fun <T> List<T>.permute(): List<List<T>> {
            val permutations: MutableList<List<T>> = mutableListOf()

            /**
             * Implements Heap's algorithm to recursively generate all the permutations of the given
             * list.
             *
             * @param size Number of elements in the list.
             * @param list List whose permutations are to be generated.
             */
            fun generate(size: Int, list: List<T>) {
                if (size == 1) {
                    permutations.add(list.toList())
                } else {
                    generate(size - 1, list)

                    for (i in 0 until size - 1) {
                        if (size % 2 == 0) {
                            Collections.swap(list, i, size - 1)
                        } else {
                            Collections.swap(list, 0, size - 1)
                        }

                        generate(size - 1, list)
                    }
                }
            }

            /* Generate the permutations. */
            generate(this.size, this.toList())

            return permutations
        }
    }
}