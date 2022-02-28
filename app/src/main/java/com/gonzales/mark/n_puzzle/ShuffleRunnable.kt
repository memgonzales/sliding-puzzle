package com.gonzales.mark.n_puzzle

import android.os.Bundle
import android.os.Handler
import android.os.Message
import java.util.concurrent.atomic.AtomicInteger

/**
 * Runnable class related to shuffling the puzzle tiles.
 *
 * @constructor Creates a runnable class related to shuffling the puzzle tiles.
 * @param handler Allows sending and processing <code>Message</code> and <code>Runnable</code> objects
 * associated with a thread's <code>MessageQueue</code>.
 * @param tilePosition Position of the tile (zero-based, following row-major order).
 * @param numTiles Number of tiles in the puzzle grid.
 */
class ShuffleRunnable(
    private val handler: Handler,
    private val tilePosition: Int,
    private val numTiles: Int
) : Runnable {
    /**
     * Companion object containing the atomic integer for tracking the progress related to shuffling
     * the puzzle tiles.
     */
    companion object {
        /**
         * Progress of the shuffling (stored in an <code>AtomicInteger</code> to prevent thread-related issues).
         */
        private val progress = AtomicInteger(0)
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used to create a thread, starting
     * the thread causes the object's <code>run</code> method to be called in that separately executing thread.
     *
     * The general contract of the method <code>run</code> is that it may take any action whatsoever.
     */
    override fun run() {
        val message = Message.obtain()

        val bundle = Bundle()
        bundle.putInt(Key.KEY_TILE_POSITION.name, tilePosition)
        bundle.putInt(Key.KEY_PROGRESS.name, progress.incrementAndGet())

        /* Subtract 1 to take blank tile into account. */
        progress.compareAndSet(numTiles - 1, 0)

        message.data = bundle
        handler.sendMessage(message)
    }
}