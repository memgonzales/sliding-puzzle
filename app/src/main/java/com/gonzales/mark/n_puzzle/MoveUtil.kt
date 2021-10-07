package com.gonzales.mark.n_puzzle

class MoveUtil {
    companion object {
        fun canMoveTile(
            direction: FlingDirection,
            position: Int,
            blankTilePos: Int,
            numColumns: Int
        ): Boolean {
            return direction == FlingDirection.UP && canMoveUp(position, blankTilePos, numColumns)
                    || direction == FlingDirection.DOWN && canMoveDown(position, blankTilePos, numColumns)
                    || direction == FlingDirection.LEFT && canMoveLeft(position, blankTilePos, numColumns)
                    || direction == FlingDirection.RIGHT && canMoveRight(position, blankTilePos, numColumns)
        }

        private fun canMoveUp(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return position == blankTilePos + numColumns
        }

        private fun canMoveDown(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return position == blankTilePos - numColumns
        }

        private fun canMoveLeft(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return !isRightEdgeTile(blankTilePos, numColumns) && position == blankTilePos + 1
        }

        private fun canMoveRight(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return !isLeftEdgeTile(blankTilePos, numColumns) && position == blankTilePos - 1
        }

        private fun isLeftEdgeTile(position: Int, numColumns: Int): Boolean {
            return position % numColumns == 0
        }

        private fun isRightEdgeTile(position: Int, numColumns: Int): Boolean {
            return position % numColumns == numColumns - 1
        }
    }
}