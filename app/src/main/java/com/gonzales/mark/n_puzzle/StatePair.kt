package com.gonzales.mark.n_puzzle

/**
 * Pair representing a puzzle state (flattened into one dimension, following row-major order)
 * alongside the position of the blank tile in the puzzle grid (zero-based, following row-major
 * order).
 *
 * Note that, although the position of the blank tile can be inferred from the puzzle state,
 * it is being tracked in the interest of efficiency (that is, to avoid linear searching).
 *
 * @constructor Creates an object that represents a puzzle state (flattened into one dimension,
 * following row-major order) alongside the position of the blank tile in the puzzle grid
 * (zero-based, following row-major order).
 * @param puzzleState Puzzle state (flattened into one dimension, following row-major order).
 * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
 * row-major order).
 */
data class StatePair(val puzzleState: ArrayList<Int>, val blankTilePos: Int)