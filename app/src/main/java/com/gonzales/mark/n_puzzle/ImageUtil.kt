package com.gonzales.mark.n_puzzle

import android.content.Context
import android.graphics.*


class ImageUtil {
    companion object {
        private const val DARK_SLATE_GRAY_FILTER = 0xFF2F4F4F

        fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
            return BitmapFactory.decodeResource(context.resources, drawableId)
        }

        fun resizeToBitmap(picture: Bitmap, dstWidth: Int, dstHeight: Int): Bitmap {
            return Bitmap.createScaledBitmap(picture, dstWidth, dstHeight, true)
        }

        fun splitBitmap(
            picture: Bitmap,
            chunkDimen: Int,
            numTiles: Int,
            numColumns: Int
        ): Pair<ArrayList<Bitmap>, ArrayList<Bitmap>> {
            val chunks: ArrayList<Bitmap> = ArrayList(numTiles)
            val blankChunks: ArrayList<Bitmap> = ArrayList(numTiles)

            for (i in 0 until numTiles) {
                val chunk: Bitmap =  Bitmap.createBitmap(
                    picture,
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

        private fun darkenBitmap(picture: Bitmap): Bitmap {
            val darkPicture: Bitmap = picture.copy(picture.config, true)
            val canvas = Canvas(darkPicture)

            val paint = Paint(Color.RED)
            paint.colorFilter = LightingColorFilter(DARK_SLATE_GRAY_FILTER.toInt(), 0x00000000)

            canvas.drawBitmap(darkPicture, Matrix(), paint)

            return darkPicture
        }
    }
}