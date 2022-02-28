package com.gonzales.mark.n_puzzle

/**
 * Utility class providing methods for moving the tiles in the puzzle grid.
 *
 * @constructor Creates an object that provides methods for moving the tiles in the puzzle grid.
 */
class MoveUtil {
    /**
     * Object containing the methods for moving the tiles in the puzzle grid.
     */
    companion object {
        /**
         * Checks if the given tile can be moved in the specified direction.
         *
         * @param direction Direction in which the tile will be moved.
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
         * row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the tile can be moved in the specified direction;
         * <code>false</code>, otherwise.
         */
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

        /**
         * Checks if the given tile can be moved up.
         *
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
         * row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the tile can be moved up; <code>false</code>, otherwise.
         */
        private fun canMoveUp(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return position == blankTilePos + numColumns
        }

        /**
         * Checks if the given tile can be moved down.
         *
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
         * row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the tile can be moved down; <code>false</code>, otherwise.
         */
        private fun canMoveDown(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return position == blankTilePos - numColumns
        }

        /**
         * Checks if the given tile can be moved to the left.
         *
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
         * row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the tile can be moved to the left; <code>false</code>, otherwise.
         */
        private fun canMoveLeft(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return !isRightEdgeTile(blankTilePos, numColumns) && position == blankTilePos + 1
        }

        /**
         * Checks if the given tile can be moved to the right.
         *
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param blankTilePos Position of the blank tile in the puzzle grid (zero-based, following
         * row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the tile can be moved to the right; <code>false</code>, otherwise.
         */
        private fun canMoveRight(position: Int, blankTilePos: Int, numColumns: Int): Boolean {
            return !isLeftEdgeTile(blankTilePos, numColumns) && position == blankTilePos - 1
        }

        /**
         * Checks if the given tile is located on the first column of the puzzle grid.
         *
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the given tile is located on the first column of the puzzle
         * grid; <code>false</code>, otherwise.
         */
        private fun isLeftEdgeTile(position: Int, numColumns: Int): Boolean {
            return position % numColumns == 0
        }

        /**
         * Checks if the given tile is located on the last column of the puzzle grid.
         *
         * @param position Position of the tile to be moved (zero-based, following row-major order).
         * @param numColumns Number of columns in the puzzle grid.
         * @return <code>true</code> if the given tile is located on the first column of the puzzle
         * grid; <code>false</code>, otherwise.
         */
        private fun isRightEdgeTile(position: Int, numColumns: Int): Boolean {
            return position % numColumns == numColumns - 1
        }
    }
}