package com.gonzales.mark.n_puzzle

import java.util.*

/**
 * Utility class providing constants and methods related to the app's timer.
 *
 * @constructor Creates an object that provides constants and methods related to the app's timer.
 */
class TimeUtil {
    /**
     * Companion object containing constants and methods related to the app's timer.
     */
    companion object {
        /**
         * Number of seconds in one hour.
         */
        private const val HOURS_TO_SECONDS = 3600

        /**
         * Number of seconds in one minute.
         */
        private const val MINUTES_TO_SECONDS = 60

        /**
         * Number of milliseconds in one second.
         */
        const val SECONDS_TO_MILLISECONDS = 1000

        /**
         * Displays the specified number of seconds in the format <code>h:mm:ss</code>, which
         * is commonly used in timers and stopwatches.
         *
         * @param seconds Number of seconds.
         * @return Number of seconds in the format <code>h:mm:ss</code>.
         */
        fun displayTime(seconds: Long): String {
            /*
             * The computation is as follows:
             *    - Hours: seconds / 3600
             *    - Minutes: (seconds % 3600) / 60
             *    - Seconds: seconds % 60
             */
            return String.format(
                Locale.getDefault(),
                "%d:%02d:%02d",
                seconds / HOURS_TO_SECONDS,
                (seconds % HOURS_TO_SECONDS) / MINUTES_TO_SECONDS,
                seconds % MINUTES_TO_SECONDS
            )
        }
    }
}