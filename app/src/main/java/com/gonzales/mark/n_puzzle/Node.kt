package com.gonzales.mark.n_puzzle

/**
 * Data class corresponding to a node.
 *
 * @param puzzleStatePair Current puzzle state (flattened into one dimension, following
 * row-major order) and the position of the blank tile in the puzzle grid (zero-based,
 * following row-major order).
 * @param parent Parent of this node (<code>null</code> if this is the start node).
 * @param g Cost of the path from the start node to this node (depth of this node).
 * @param h Heuristic for estimating the cheapest path from this node to the goal (Manhattan distance).
 */
data class Node(
    val puzzleStatePair: StatePair,
    val parent: Node?,
    var g: Int,
    private var h: Int
) {
    /**
     * Companion object containing the method for hashing the puzzle state of this node.
     */
    companion object {
        /**
         * Returns a hash code for the puzzle state of this node.
         *
         * To ensure uniqueness and maintain computational efficiency, the hash code is an integer
         * formed from flattening the puzzle state into a single dimension, following row-major order.
         */
        fun hashState(puzzleState: ArrayList<Int>): Int {
            var hash = 0
            for (tile in puzzleState) {
                hash = hash * 10 + tile
            }

            return hash
        }
    }

    /**
     * Computes the f-value of this node (in relation to the A* algorithm).
     *
     * The f-value is the sum of the g- and h-values.
     */
    fun getF(): Int {
        return g + h
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * The <code>equals</code> method implements an equivalence relation on non-null object references:
     *
     * <ul>
     *     <li> It is reflexive: for any non-null reference value <code>x</code>, <code>x.equals(x)</code>
     *     should return <code>true</code>. </li>
     *     <li> It is symmetric: for any non-null reference values <code>x</code> and <code>y</code>,
     *     <code>x.equals(y)</code> should return <code>true</code> if and only if <code>y.equals(x)</code>
     *     returns <code>true</code>. </li>
     *     <li> It is transitive: for any non-null reference values <code>x</code>, <code>y</code>,
     *     and <code>z</code>, if <code>x.equals(y)</code> returns <code>true</code> and
     *     <code>y.equals(z)</code> returns <code>true</code>, then <code>x.equals(z)</code> should
     *     return <code>true</code>. </li>
     *     <li> It is consistent: for any non-null reference values <code>x</code> and <code>y</code>,
     *     multiple invocations of <code>x.equals(y)</code> consistently return <code>true</code>
     *     or consistently return <code>false</code>, provided no information used in equals comparisons
     *     on the objects is modified. </li>
     *     <li> For any non-null reference value x, x.equals(null) should return false. </li>
     * </ul>
     *
     * @param other The reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj argument; <code>false</code>
     * otherwise.
     */
    override fun equals(other: Any?): Boolean {
        return this.puzzleStatePair.puzzleState == (other as Node).puzzleStatePair.puzzleState
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash
     * tables such as those provided by <code>HashMap</code>.
     *
     * The way this method is overridden ensures that the hash code is dependent solely on the
     * puzzle state of this node.
     *
     * @return A hash code value for this object.
     */
    override fun hashCode(): Int {
        return hashState(puzzleStatePair.puzzleState)
    }
}