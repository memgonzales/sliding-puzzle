package com.gonzales.mark.n_puzzle

/**
 * Comparator for nodes based on their f-values (in reference to the A* algorithm).
 *
 * @constructor Creates a comparator for nodes based on their f-values (in reference to the A* algorithm).
 */
class NodeByF: Comparator<Node> {
    /**
     * Compares its two arguments for order. Returns zero if the arguments are equal, a negative number
     * if the first argument is less than the second, or a positive number if the first argument is
     * greater than the second.
     *
     * @param o1 First object to be compared.
     * @param o2 Second object to be compared.
     *
     * @return A negative integer, zero, or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     */
    override fun compare(o1: Node?, o2: Node?): Int {
        if (o1?.getF()!! < o2?.getF()!!) {
            return -1
        } else if (o1.getF() > o2.getF()) {
            return 1
        }

        return 0
    }
}