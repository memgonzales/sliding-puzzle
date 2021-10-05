package com.gonzales.mark.n_puzzle

class MoveUtil {
    companion object {
        private const val MAX_NUM_NEIGHBORS = 4
        private const val NO_TILE = -1

        fun canMoveTile(
            direction: FlingDirection,
            position: Int,
            blankTilePos: Int,
            numColumns: Int
        ): Boolean {
            return Pair(direction, position) in getValidFlings(
                getNeighborsBlank(
                    blankTilePos,
                    numColumns
                )
            )
        }

        private fun getValidFlings(neighbors: ArrayList<Int>): ArrayList<Pair<FlingDirection, Int>> {
            val validFlings =
                ArrayList<Pair<FlingDirection, Int>>(MAX_NUM_NEIGHBORS)
            val directions = FlingDirection.values()

            for (fling in directions.zip(neighbors)) {
                validFlings.add(Pair(fling.first, fling.second))
            }

            return validFlings
        }

        private fun getNeighborsBlank(blankTilePos: Int, numColumns: Int): ArrayList<Int> {
            val neighbors = ArrayList<Int>(MAX_NUM_NEIGHBORS)

            /*
             * The order of addition into the neighbors ArrayList is important in the implementation
             * of getValidFlings().
             *
             * The diametrically opposite direction is added following the order of elements
             *     in the FlingDirection enumeration class. To illustrate: since the first
             *     element in the FlingDirection enumeration class is UP, the first neighbor
             *     added is the bottom one.
             */

            /* Bottom neighbor */
            neighbors.add(blankTilePos + numColumns)

            /* Top neighbor */
            neighbors.add(blankTilePos - numColumns)

            /* Right neighbor */
            if (!isRightEdgeTile(blankTilePos, numColumns)) {
                neighbors.add(blankTilePos + 1)
            } else {
                neighbors.add(NO_TILE)
            }

            /* Left neighbor */
            if (!isLeftEdgeTile(blankTilePos, numColumns)) {
                neighbors.add(blankTilePos - 1)
            } else {
                neighbors.add(NO_TILE)
            }

            return neighbors
        }

        private fun isLeftEdgeTile(position: Int, numColumns: Int): Boolean {
            return position % numColumns == 0
        }

        private fun isRightEdgeTile(position: Int, numColumns: Int): Boolean {
            return position % numColumns == numColumns - 1
        }
    }
}