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
        const val SHUFFLING_ANIMATION_UPPER_BOUND = 2700

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

        /**
         * Delay before the first move in the animation of the puzzle solution is displayed.
         */
        const val FIRST_MOVE_SOLUTION_DELAY = 750

        /**
         * Delay between the sliding of tiles during the animation of the puzzle solution.
         */
        const val MOVE_SOLUTION_DELAY = 425

        /**
         * Duration of the display of the fadeout of the first iteration of the logo frame animation
         * in the splash screen.
         */
        const val ANIMATION_FRAME_FADEOUT = 400

        /**
         * Duration of the display of the splash screen.
         *
         * This should be set to a value less than 3000 ms, per the recommendation of Campbell
         * (2018): https://uxdesign.cc/building-the-perfect-splash-screen-46e080395f06.
         */
        const val SPLASH_SCREEN_TIMEOUT = 2000
    }
}