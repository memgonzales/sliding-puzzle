package com.gonzales.mark.n_puzzle

/**
 * Utility class providing constants related to timing the animations in the app.
 *
 * @constructor Creates an object that provides constants related to timing the animations in the app.
 */
class AnimationUtil {
    /**
     * Companion object containing the constants related to timing the animations in the app.
     *
     * The constants are given in milliseconds, unless otherwise stated.
     */
    companion object {
        /**
         * Upper bound of the duration of the animation of the progress bar during shuffling.
         *
         * Following the implementation in <code>startShowingTiles</code> (found in
         * <code>NPuzzleActivity</code> class), the actual duration of this animation is
         * between <code>SHUFFLING_ANIMATION_OFFSET</code> and
         * <code>SHUFFLING_ANIMATION_UPPER_BOUND + SHUFFLING_ANIMATION_OFFSET</code>,
         * inclusive.
         */
        const val SHUFFLING_ANIMATION_UPPER_BOUND = 3000

        /**
         * Offset of the duration of the animation of the progress bar during shuffling.
         *
         * Following the implementation in <code>startShowingTiles</code> (found in
         * <code>NPuzzleActivity</code> class), the actual duration of this animation is
         * between <code>SHUFFLING_ANIMATION_OFFSET</code> and
         * <code>SHUFFLING_ANIMATION_UPPER_BOUND + SHUFFLING_ANIMATION_OFFSET</code>,
         * inclusive.
         */
        const val SHUFFLING_ANIMATION_OFFSET = 300

        /**
         * Maximum duration of the display of the success message once a game is completed
         * (that is, the shuffled puzzle is solved).
         *
         * The success message can be prematurely hidden by interacting with the UI
         * (for example, clicking the shuffle button and moving a tile).
         */
        const val SUCCESS_DISPLAY = 5000

        const val SOLUTION_ANIMATION = 750
    }
}