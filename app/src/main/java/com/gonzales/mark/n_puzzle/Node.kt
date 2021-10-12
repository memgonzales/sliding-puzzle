package com.gonzales.mark.n_puzzle

class Node(
    private val puzzleState: ArrayList<Int>,
    private val blankTilePos: Int,
    private var g: Int,
    private var h: Int
) :
    Comparator<Node> {
    fun getState(): ArrayList<Int> {
        return puzzleState
    }

    private fun getF(): Int {
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

    override fun compare(o1: Node?, o2: Node?): Int {
        if (o1?.getF()!! < o2?.getF()!!) {
            return -1
        }

        if (o1.getF() > o2.getF()) {
            return 1
        }

        return 0
    }

    override fun toString(): String {
        return "$puzzleState\ng: $g h: $h\n"
    }
}