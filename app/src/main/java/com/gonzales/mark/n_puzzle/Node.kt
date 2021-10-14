package com.gonzales.mark.n_puzzle

class Node(
    private val puzzleState: ArrayList<Int>,
    private val blankTilePos: Int,
    private var g: Int,
    private var h: Int
) {
    fun getState(): ArrayList<Int> {
        return puzzleState
    }

    fun getBlankTilePos(): Int {
        return blankTilePos
    }

    fun getF(): Int {
        return g + h
    }

    fun getG(): Int {
        return g
    }

    fun setG(g: Int) {
        this.g = g
    }

    fun setH(h: Int) {
        this.h = h
    }

    fun hash(): Int {
        var hash = 0
        for (tile in puzzleState) {
            hash = hash * 10 + tile
        }

        return hash
    }

    override fun equals(other: Any?): Boolean {
        return this.puzzleState == (other as Node).puzzleState
    }

    override fun hashCode(): Int {
        return hash()
    }

    override fun toString(): String {
        return "\n$puzzleState\ng: $g h: $h\n"
    }
}