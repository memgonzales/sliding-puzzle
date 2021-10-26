package com.gonzales.mark.n_puzzle

enum class SolveStatus(val successMessageId: Int) {
    USER_SOLVED(R.string.user_solved),
    FEWEST_MOVES(R.string.high_score),
    FASTEST_TIME(R.string.high_score),
    FEWEST_AND_FASTEST(R.string.high_score),
    COMPUTER_SOLVED(R.string.computer_solved);
}