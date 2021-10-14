package com.gonzales.mark.n_puzzle

class Node(
    private val puzzleState: ArrayList<Int>,
    private val blankTilePos: Int,
    private var parent: Node?,
    private var g: Int,
    private var h: Int
) {
    companion object {
        fun hashState(puzzleState: ArrayList<Int>): Int {
            var hash = 0
            for (tile in puzzleState) {
                hash = hash * 10 + tile
            }

            return hash
        }
    }

    fun getState(): ArrayList<Int> {
        return puzzleState
    }

    fun getBlankTilePos(): Int {
        return blankTilePos
    }

    fun getParent(): Node? {
        return parent
    }

    fun getG(): Int {
        return g
    }

    fun getF(): Int {
        return g + h
    }

    fun hash(): Int {
        return hashState(puzzleState)
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