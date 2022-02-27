package com.gonzales.mark.n_puzzle

/**
 * Enumeration class containing the possible game statuses after the user solves the puzzle.
 */
enum class SolveStatus(val successMessageId: Int) {
    /**
     * The user solved the puzzle but did not register a new value for the fewest number of moves
     * or the fastest time.
     */
    USER_SOLVED(R.string.user_solved),

    /**
     * The user registered a new value for
     */
    FEWEST_MOVES(R.string.fewest_score),
    FASTEST_TIME(R.string.fastest_score),
    FEWEST_AND_FASTEST(R.string.high_score),
    COMPUTER_SOLVED(R.string.computer_solved);
}