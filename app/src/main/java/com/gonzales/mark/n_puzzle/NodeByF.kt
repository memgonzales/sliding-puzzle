package com.gonzales.mark.n_puzzle

class NodeByF: Comparator<Node> {
    override fun compare(o1: Node?, o2: Node?): Int {
        if (o1?.getF()!! < o2?.getF()!!) {
            return -1
        } else if (o1.getF() > o2.getF()) {
            return 1
        }

        return 0
    }
}