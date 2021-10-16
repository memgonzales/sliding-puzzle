package com.gonzales.mark.n_puzzle

class State(private val puzzleState: ArrayList<Int>, private val blankTilePos: Int) {
    fun getPuzzleState(): ArrayList<Int> {
        return puzzleState
    }

    fun getBlankTilePos(): Int {
        return blankTilePos
    }
}