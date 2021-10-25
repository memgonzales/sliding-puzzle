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

        /**
         * Generates all the permutations of this list using Heap's algorithm.
         *
         * Heap's algorithm was introduced by B.R. Heap in the 1963 paper "Permutations by Interchanges,"
         * which was published in <i>The Computer Journal</i> (Volume 6, Issue 3). The implementation
         * in this method is the recursive version.
         *
         * @return List of all the permutations of this list.
         */
        fun <T> List<T>.permute(): List<List<T>> {
            val permutations: MutableList<List<T>> = mutableListOf()

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

            generate(this.size, this.toList())

            return permutations
        }
    }
}