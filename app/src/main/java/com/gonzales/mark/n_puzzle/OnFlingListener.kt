package com.gonzales.mark.n_puzzle

/**
 * Interface for responding to detected fling gestures.
 */
interface OnFlingListener {
    /**
     * Defines the response to the detected fling gesture given the fling direction and the position
     * of the flung tile in the puzzle grid.
     *
     * @param direction Direction of the fling gesture.
     * @param position Position of the flung tile in the puzzle grid (zero-based, following row major
     * order).
     */
    fun onFling(direction: FlingDirection, position: Int)
}