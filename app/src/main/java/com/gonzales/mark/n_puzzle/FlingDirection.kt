package com.gonzales.mark.n_puzzle

/**
 * Enumeration class containing the directions of the fling gestures registered by the app (namely,
 * the four cardinal directions) and an <code>INVALID</code> entry for unrecognized directions.
 *
 * @constructor Creates an enumeration that contains the directions of the fling gestures registered
 * by the app (namely, the four cardinal directions) and an <code>INVALID</code> entry for an
 * unrecognized direction.
 */
enum class FlingDirection {
    /**
     * Upward fling direction.
     */
    UP,

    /**
     * Downward fling direction.
     */
    DOWN,

    /**
     * Left fling direction.
     */
    LEFT,

    /**
     * Right fling direction.
     */
    RIGHT,

    /**
     * Unrecognized fling direction.
     */
    INVALID
}