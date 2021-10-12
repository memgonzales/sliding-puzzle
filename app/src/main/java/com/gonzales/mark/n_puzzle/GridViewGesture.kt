package com.gonzales.mark.n_puzzle

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.GridView
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Class extending <code>GridView</code> to register fling gestures.
 */
class GridViewGesture : GridView {
    /**
     * Detects gestures and events using the supplied <code>MotionEvent</code>s.
     */
    private lateinit var gestureDetector: GestureDetector

    /**
     * Responds to the detected fling gestures.
     */
    private lateinit var flingListener: OnFlingListener

    /**
     * <code>true</code> is a fling gesture is occurring; <code>false</code>, otherwise.
     */
    private var isFlinging: Boolean = false

    /**
     * x-coordinate related to the start of the most recent pressure gesture.
     */
    private var downX: Float = 0f

    /**
     * y-coordinate related to the start of the most recent pressure gesture.
     */
    private var downY: Float = 0f

    /**
     * Distance in pixels a touch can wander before it is registered as a fling gesture.
     */
    private var touchSlopThreshold: Int = 0

    /**
     * Creates an object that extends <code>GridView</code> to register fling gestures.
     *
     * @param context Context of the application environment.
     */
    constructor(context: Context) : super(context) {
        detectGesture(context)
    }

    /**
     * Creates an object that extends <code>GridView</code> to register fling gestures.
     *
     * @param context Context of the application environment.
     * @param attrs Collection of attributes.
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        detectGesture(context)
    }

    /**
     * Creates an object that extends <code>GridView</code> to register fling gestures.
     *
     * @param context Context of the application environment.
     * @param attrs Collection of attributes.
     * @param defStyleAttr An attribute in the current theme that contains a reference to a style
     * resource that supplies defaults values for the <code>StyledAttributes</code>.
     * Can be 0 to not look for defaults.
     */
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        detectGesture(context)
    }

    /**
     * Creates an object that extends <code>GridView</code> to register fling gestures.
     *
     * @param context Context of the application environment.
     * @param attrs Collection of attributes.
     * @param defStyleAttr An attribute in the current theme that contains a reference to a style
     * resource that supplies defaults values for the <code>StyledAttributes</code>.
     * Can be 0 to not look for defaults.
     * @param defStyleRes A resource identifier of a style resource that supplies default values
     * for the <code>StyledAttributes</code>, used only if <code>defStyleAttr</code> is 0 or can
     * not be found in the theme. Can be 0 to not look for defaults.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        detectGesture(context)
    }

    /**
     * Sets the listener for responding to detected fling gestures.
     *
     * @param flingListener Listener for responding to detected fling gestures.
     */
    fun setFlingListener(flingListener: OnFlingListener) {
        this.flingListener = flingListener
    }

    /**
     * Sets the distance in pixels a touch can wander before it is registered as a fling gesture.
     *
     * @param touchSlopThreshold Distance in pixels a touch can wander before it is registered
     * as a fling gesture.
     */
    fun setTouchSlopThreshold(touchSlopThreshold: Int) {
        this.touchSlopThreshold = touchSlopThreshold
    }

    /**
     * Sets the detector for the gestures and events.
     *
     * @param context Context of the application environment.
     */
    private fun detectGesture(context: Context) {
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

                /**
                 * Notified when a tap occurs with the down MotionEvent that triggered it.
                 * This will be triggered immediately for every down event.
                 * All other events should be preceded by this.
                 *
                 * @param event The down motion event.
                 * @return <code>true</code> if the event is consumed; else <code>false</code>
                 */
                override fun onDown(event: MotionEvent): Boolean {
                    return true
                }

                /**
                 * Notified of a fling event when it occurs with the initial on down <code>MotionEvent</code>
                 * and the matching up <code>MotionEvent</code>. The calculated velocity is supplied along
                 * the x and y axis in pixels per second.
                 *
                 * @param e1 The first down motion event that started the fling.
                 * @param e2 The move motion event that triggered the current onFling.
                 * @param velocityX The velocity of this fling measured in pixels per second along the x axis.
                 * @param velocityY The velocity of this fling measured in pixels per second along the y axis.
                 * @return <code>true</code> if the event is consumed; else <code>false</code>
                 */
                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val position: Int = pointToPosition(e1.x.roundToInt(), e1.y.roundToInt())
                    val direction: FlingDirection = FlingDetector.getDirection(e1, e2)
                    flingListener.onFling(direction, position)

                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })
    }

    /**
     * Implement this method to handle touch screen motion events.
     *
     * @param ev The motion event.
     * @return <code>true</code> if the event is consumed; else <code>false</code>
     */
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.actionMasked == MotionEvent.ACTION_UP) {
            performClick()
        }

        return gestureDetector.onTouchEvent(ev)
    }

    /**
     * Call this view's <code>OnClickListener</code>, if it is defined.
     * Performs all normal actions associated with clicking: reporting accessibility event,
     * playing a sound, etc.
     *
     * @return <code>true</code> there was an assigned <code>OnClickListener</code> that was called,
     * <code>false</code> otherwise is returned
     */
    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

    /**
     * Implement this method to intercept all touch screen motion events. This allows you to watch
     * events as they are dispatched to your children, and take ownership of the current gesture at
     * any point.
     *
     * @param ev The motion event being dispatched down the hierarchy.
     * @return Return <code>true</code> to steal motion events from the children and have them dispatched
     * to this <code>ViewGroup</code> through <code>onTouchEvent()</code>. The current target will receive
     * an <code>ACTION_CANCEL</code> event, and no further messages will be delivered here.
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(ev)

        when (ev!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                isFlinging = false
            }

            MotionEvent.ACTION_MOVE -> {
                if (isFlinging) {
                    return true
                }

                /* Check if the difference between the coordinates is sufficient to be considered a fling. */
                val deltaX: Float = abs(ev.x - downX)
                val deltaY: Float = abs(ev.y - downY)

                if (deltaX > touchSlopThreshold || deltaY > touchSlopThreshold) {
                    isFlinging = true
                    return true
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }
}