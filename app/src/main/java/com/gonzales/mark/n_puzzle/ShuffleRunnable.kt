package com.gonzales.mark.n_puzzle

import android.os.Bundle
import android.os.Handler
import android.os.Message
import java.util.concurrent.atomic.AtomicInteger

class ShuffleRunnable(
    private val handler: Handler,
    private val tilePosition: Int,
    private val numTiles: Int
) : Runnable {
    companion object {
        private val progress = AtomicInteger(0)
    }

    override fun run() {
        val message = Message.obtain()

        val bundle = Bundle()
        bundle.putInt(Key.KEY_TILE_POSITION.name, tilePosition)
        bundle.putInt(Key.KEY_PROGRESS.name, progress.incrementAndGet())

        /* Subtract 1 to take blank tile into account. */
        if (progress.toInt() >= numTiles - 1) {
            progress.set(0)
        }

        message.data = bundle

        handler.sendMessage(message)
    }

}