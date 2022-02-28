package com.gonzales.mark.n_puzzle

import android.content.Context
import android.graphics.*

/**
 * Utility class providing constants and methods for manipulating the puzzle image.
 *
 * @constructor Creates an object that provides methods for manipulating the puzzle image.
 */
class ImageUtil {
    /**
     * Companion object containing the constants and methods for manipulating the puzzle image.
     */
    companion object {
        /**
         * Dark color filter for distinguishing the blank tile from the rest of the tiles.
         */
        private const val DARK_GRAY_FILTER = 0x121f1f

        /**
         * Converts the given drawable to a bitmap.
         *
         * @param context Context of the application environment.
         * @param drawableId ID of the drawable to be converted to a bitmap.
         * @return Bitmap resulting from the conversion of the given drawable.
         */
        fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
            return BitmapFactory.decodeResource(context.resources, drawableId)
        }

        /**
         * Resizes (crops) the given bitmap such that its dimensions are equal.
         *
         * @param image Bitmap to be resized (cropped) to a square.
         * @param dstWidth Width of the resulting bitmap.
         * @param dstHeight Height of the resulting bitmap.
         * @return Resized (cropped) bitmap with equal dimensions.
         */
        fun resizeToSquareBitmap(image: Bitmap, dstWidth: Int, dstHeight: Int): Bitmap {
            return Bitmap.createScaledBitmap(cropToSquareBitmap(image), dstWidth, dstHeight, true)
        }

        /**
         * Crops the given bitmap such that its dimensions are equal.
         *
         * The dimension of the resulting bitmap follows the smaller value between the original
         * width and height.
         *
         * @param image Bitmap to be cropped to a square.
         * @return Cropped bitmap with equal dimensions.
         */
        private fun cropToSquareBitmap(image: Bitmap): Bitmap {
            if (image.width >= image.height) {
                return Bitmap.createBitmap(
                    image,
                    image.width / 2 - image.height / 2,
                    0,
                    image.height,
                    image.height
                )
            } else {
                return Bitmap.createBitmap(
                    image,
                    0,
                    image.height / 2 - image.width / 2,
                    image.width,
                    image.width
                )
            }
        }

        /**
         * Splits the given bitmap into multiple square chunks.
         *
         * This method returns a pair of <code>ArrayList</code>:
         * <ul>
         *     <li> The first contains the chunks. </li>
         *     <li> The second contains the sme chunks but with a dark color filter applied.
         *     The dark color filter is used to distinguish the blank tile from the rest of the
         *     puzzle tiles.<li>
         * </ul>
         *
         *
         * @param image Bitmap to be split into multiple chunks.
         * @param chunkDimen Dimension of a chunk.
         * @param numTiles Number of chunks (should be equal to the square of <code>numColumns</code>).
         * @param numColumns Number of columns in the puzzle grid.
         * @return Pair of <code>ArrayList</code>, with the first containing the chunks and the second
         * containing the same chunks but with a dark color filter applied.
         */
        fun splitBitmap(
            image: Bitmap,
            chunkDimen: Int,
            numTiles: Int,
            numColumns: Int
        ): Pair<ArrayList<Bitmap>, ArrayList<Bitmap>> {
            val chunks: ArrayList<Bitmap> = ArrayList(numTiles)
            val blankChunks: ArrayList<Bitmap> = ArrayList(numTiles)

            for (i in 0 until numTiles) {
                val chunk: Bitmap = Bitmap.createBitmap(
                    image,
                    (i % numColumns) * chunkDimen,
                    (i / numColumns) * chunkDimen,
                    chunkDimen,
                    chunkDimen
                )

                chunks.add(chunk)
                /* Darken the blank tile. */
                blankChunks.add(darkenBitmap(chunk))
            }

            return Pair(chunks, blankChunks)
        }

        /**
         * Applies a dark color filter on the given bitmap to designate it as the blank tile
         * and distinguish it from the rest of the puzzle tiles.
         *
         * @param image Bitmap on which the dark color filter is applied.
         * @return Bitmap with the dark color filter applied.
         */
        private fun darkenBitmap(image: Bitmap): Bitmap {
            val darkPicture: Bitmap = image.copy(image.config, true)
            val canvas = Canvas(darkPicture)

            val paint = Paint(Color.RED)
            paint.colorFilter = LightingColorFilter(DARK_GRAY_FILTER, 0x00000000)

            canvas.drawBitmap(darkPicture, Matrix(), paint)

            return darkPicture
        }
    }
}