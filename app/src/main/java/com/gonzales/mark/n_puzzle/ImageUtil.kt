package com.gonzales.mark.n_puzzle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class ImageUtil {
    companion object {
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
        ): ArrayList<Bitmap> {
            val chunks: ArrayList<Bitmap> = ArrayList(numTiles)

            for (i in 0 until numTiles) {
                chunks.add(
                    Bitmap.createBitmap(
                        picture,
                        (i % numColumns) * chunkDimen,
                        (i / numColumns) * chunkDimen,
                        chunkDimen,
                        chunkDimen
                    )
                )
            }

            return chunks
        }
    }
}