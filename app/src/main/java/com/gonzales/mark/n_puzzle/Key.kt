package com.gonzales.mark.n_puzzle

/**
 * Enumeration class containing the keys used in handling bundles and shared preferences.
 *
 * @constructor Creates an enumeration class containing the keys used in handling bundles and shared preferences.
 */
enum class Key {
    /**
     * Key for handling bundles related to the position of the tile during shuffling.
     */
    KEY_TILE_POSITION,

    /**
     * Key for handling bundles related to the progress of the shuffling.
     */
    KEY_PROGRESS,

    /**
     * Key for handling shared preferences related to the fewest number of moves taken by the user
     * to solve the puzzle.
     */
    KEY_FEWEST_MOVES,

    /**
     * Key for handling shared preferences related to the fastest time taken by user to solve
     * the puzzle.
     */
    KEY_FASTEST_TIME,

    /**
     * Key for handling shared preferences related to the most recent puzzle image selected
     * by the user.
     */
    KEY_PUZZLE_IMAGE
}