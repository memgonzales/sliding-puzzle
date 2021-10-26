package com.gonzales.mark.n_puzzle

import android.content.Context
import android.graphics.*

class ImageUtil {
    companion object {
        private const val DARK_GRAY_FILTER = 0x121f1f

        fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
            return BitmapFactory.decodeResource(context.resources, drawableId)
        }

        fun resizeToSquareBitmap(image: Bitmap, dstWidth: Int, dstHeight: Int): Bitmap {
            return Bitmap.createScaledBitmap(cropToSquareBitmap(image), dstWidth, dstHeight, true)
        }

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
                blankChunks.add(darkenBitmap(chunk))
            }

            return Pair(chunks, blankChunks)
        }

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