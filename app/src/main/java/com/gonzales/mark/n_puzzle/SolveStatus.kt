package com.gonzales.mark.n_puzzle

/**
 * Enumeration class containing the possible game statuses depending on whether user solves the puzzle
 * or opts to play the solution walkthrough instead.
 *
 * @constructor Creates an enumeration class containing the possible game statuses after the user solves the puzzle.
 * @param successMessageId ID of the string corresponding to the success message displayed.
 */
enum class SolveStatus(val successMessageId: Int) {
    /**
     * The user solved the puzzle but did not register a new record for the fewest number of moves
     * or the fastest time.
     */
    USER_SOLVED(R.string.user_solved),

    /**
     * The user registered a new record for the fewest number of moves.
     */
    FEWEST_MOVES(R.string.fewest_score),

    /**
     * The user registered a new record for the fastest time.
     */
    FASTEST_TIME(R.string.fastest_score),

    /**
     * The user registered a new record for both the fewest number of moves and the fastest time.
     */
    FEWEST_AND_FASTEST(R.string.high_score),

    /**
     * The user opted to display the solution returned by the app (via A*).
     */
    COMPUTER_SOLVED(R.string.computer_solved);
}