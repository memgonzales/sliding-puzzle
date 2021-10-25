package com.gonzales.mark.n_puzzle

import org.junit.Test
import java.util.Collections.swap

class ShuffleUtilTest {
    companion object {
        /**
         * Generates all the permutations of a list using Heap's algorithm.
         *
         * Heap's algorithm was introduced by B.R. Heap in the 1963 paper "Permutations by Interchanges,"
         * which was published in <i>The Computer Journal</i> (Volume 6, Issue 3). The implementation
         * in this method is the recursive version.
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
                            swap(list, i, size - 1)
                        } else {
                            swap(list, 0, size - 1)
                        }

                        generate(size - 1, list)
                    }
                }
            }

            generate(this.size, this.toList())
            return permutations
        }
    }

    @Test
    fun `Check if solutions to solvable 8-puzzles exist`() {

    }

    @Test
    fun `Check if solutions to unsolvable 8-puzzles do not exist`() {

    }

    @Test
    fun `Check if there is an equal number of solvable and unsolvable 8-puzzles`() {

    }
}