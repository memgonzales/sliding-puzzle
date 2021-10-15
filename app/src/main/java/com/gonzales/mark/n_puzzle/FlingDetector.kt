package com.gonzales.mark.n_puzzle

import android.view.MotionEvent
import kotlin.math.PI
import kotlin.math.atan2

/**
 * Class providing the constants and methods for identifying the direction of a fling gesture.
 *
 * @constructor Creates an object that provides the constants and methods for identifying the
 * direction of a fling gesture.
 */
class FlingDetector {
    /**
     * Companion object containing the constants and methods for identifying the direction
     * of a fling gesture.
     */
    companion object {
        /**
         * Boundary angles (in radians) for distinguishing the direction of a fling gesture.
         *
         * The direction of a fling gesture is registered as:
         * <ul>
         *  <li> <code>FlingDirection.DOWN</code> if the angle is in [0.25π, 0.75π). </li>
         *  <li> <code>FlingDirection.UP</code> if the angle is in [1.25π, 1.75π). </li>
         *  <li> <code>FlingDirection.RIGHT</code> if the angle is in [0.75π, 1.25π). </li>
         *  <li> <code>FlingDirection.LEFT</code> if the angle is in [0, 0.25π)
         *      or [1.75π, 2π). </li>
         * </ul>
         */
        private val BOUNDARY_ANGLES =
            doubleArrayOf(0.0, 0.25 * PI, 0.75 * PI, 1.25 * PI, 1.75 * PI, 2 * PI)

        /**
         * Returns the direction of the fling gesture based on the angle formed between the
         * points defined by the coordinates of the given <code>MotionEvent</code>s.
         *
         * The directions identified are limited to the four cardinal directions.
         *
         * @param e1 First down motion event that started the fling.
         * @param e2 Move motion event that triggered the current <code>onFling</code>.
         * @return Direction of the fling gesture.
         */
        fun getDirection(
            e1: MotionEvent,
            e2: MotionEvent,
        ): FlingDirection {
            val angle: Double = getAngle(e1.x, e1.y, e2.x, e2.y)

            if (isUp(angle)) {
                return FlingDirection.UP
            }

            if (isDown(angle)) {
                return FlingDirection.DOWN
            }

            if (isLeft(angle)) {
                return FlingDirection.LEFT
            }

            if (isRight(angle)) {
                return FlingDirection.RIGHT
            }

            /* Since the code covers all possible angles, this code should be unreachable. */
            return FlingDirection.INVALID
        }

        /**
         * Computes the angle (in radians) between the points defined by the given coordinates.
         *
         * Internally, this method adds π to the value returned by
         * [<code>atan2</code>](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.math/atan2.html).
         * Therefore, the angle returned by this method is in the range 0 to 2π (inclusive),
         * provided that it is defined.
         *
         * @param x1 x-coordinate of the first down motion event that started the fling.
         * @param y1 y-coordinate of the first down motion event that started the fling.
         * @param x2 x-coordinate of the move motion event that triggered the current <code>onFling</code>.
         * @param y2 y-coordinate of the move motion event that triggered the current <code>onFling</code>.
         * @return Angle (in radians) between the points defined by the given coordinates.
         */
        private fun getAngle(x1: Float, y1: Float, x2: Float, y2: Float): Double {
            /*
             * Add π since it is more convenient to work with values in the range [0, 2π]
             * compared to those in atan2's default [-π, π].
             */
            return atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()) + PI
        }

        /**
         * Checks if the angle formed between the points defined by the coordinates of the pertinent
         * <code>MotionEvent</code>s is indicative of an upward gesture.
         *
         * An upward gesture is registered if the angle is in [1.25π, 1.75π).
         *
         * @param angle Angle (in radians) formed between the points defined by the coordinates of the
         * pertinent <code>MotionEvent</code>s.
         * @return <code>true</code> if the angle formed between the points defined by the coordinates
         * of the pertinent <code>MotionEvent</code>s is indicative of an upward gesture.
         */
        private fun isUp(angle: Double): Boolean {
            return BOUNDARY_ANGLES[3] <= angle && angle <= BOUNDARY_ANGLES[4]
        }

        /**
         * Checks if the angle formed between the points defined by the coordinates of the pertinent
         * <code>MotionEvent</code>s is indicative of a downward gesture.
         *
         * A downward gesture is registered if the angle is in [0.25π, 0.75π).
         *
         * @param angle Angle (in radians) formed between the points defined by the coordinates of the
         * pertinent <code>MotionEvent</code>s.
         * @return <code>true</code> if the angle formed between the points defined by the coordinates
         * of the pertinent <code>MotionEvent</code>s is indicative of a downward gesture.
         */
        private fun isDown(angle: Double): Boolean {
            return BOUNDARY_ANGLES[1] <= angle && angle <= BOUNDARY_ANGLES[2]
        }

        /**
         * Checks if the angle formed between the points defined by the coordinates of the pertinent
         * <code>MotionEvent</code>s is indicative of a left gesture.
         *
         * A left gesture is registered if the angle is in [0, 0.25π)
         * or [1.75π, 2π).
         *
         * @param angle Angle (in radians) formed between the points defined by the coordinates of the
         * pertinent <code>MotionEvent</code>s.
         * @return <code>true</code> if the angle formed between the points defined by the coordinates
         * of the pertinent <code>MotionEvent</code>s is indicative of a left gesture.
         */
        private fun isLeft(angle: Double): Boolean {
            return BOUNDARY_ANGLES[0] <= angle && angle <= BOUNDARY_ANGLES[1]
                    || BOUNDARY_ANGLES[4] <= angle && angle <= BOUNDARY_ANGLES[5]
        }

        /**
         * Checks if the angle formed between the points defined by the coordinates of the pertinent
         * <code>MotionEvent</code>s is indicative of a right gesture.
         *
         * A right gesture is registered if the angle is in [0.75π, 1.25π).
         *
         * @param angle Angle (in radians) formed between the points defined by the coordinates of the
         * pertinent <code>MotionEvent</code>s.
         * @return <code>true</code> if the angle formed between the points defined by the coordinates
         * of the pertinent <code>MotionEvent</code>s is indicative of a right gesture.
         */
        private fun isRight(angle: Double): Boolean {
            return BOUNDARY_ANGLES[2] <= angle && angle <= BOUNDARY_ANGLES[3]
        }
    }
}