package com.gonzales.mark.n_puzzle

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * Class handling the display of the splash screen and the loading of initial resources needed during
 * app startup.
 */
class SplashScreenActivity : AppCompatActivity() {
    /**
     * Animated logo of the app.
     */
    private lateinit var logoAnimation: AnimationDrawable

    /**
     * Called when the activity is first created. This is where you should do all of your normal
     * static set up: create views, bind data to lists, etc. This method also provides you with a
     * Bundle containing the activity's previously frozen state, if there was one.
     * Always followed by <code>onStart()</code>.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is null. This value may be
     * <code>null</code>.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initSplashScreen()
        launchApp()
    }

    /**
     * Called when the current Window of the activity gains or loses focus.
     *
     * Since the splash screen features an animated logo, this function is overridden to keep track
     * of when the splash screen activity gains focus (that is, upon launching) and subsequently
     * trigger the animation of the logo.
     *
     * @param hasFocus Whether the window of this activity has focus.
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        animateSplashScreen()
    }

    /**
     * Initializes the components of the splash screen.
     */
    private fun initSplashScreen() {
        val ivLogo = findViewById<ImageView>(R.id.iv_splash_logo)
        ivLogo.setBackgroundResource(R.drawable.logo_animation)
        logoAnimation = ivLogo.background as AnimationDrawable
    }

    /**
     * Starts the animation of the logo featured on the splash screen.
     */
    private fun animateSplashScreen() {
        logoAnimation.setExitFadeDuration(AnimationUtil.ANIMATION_FRAME_FADEOUT)
        logoAnimation.start()
    }

    /**
     * Launches the activity immediately following the splash screen after a set duration.
     */
    private fun launchApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@SplashScreenActivity, NPuzzleActivity::class.java)
            startActivity(i)
            finish()
        }, AnimationUtil.SPLASH_SCREEN_TIMEOUT.toLong())
    }
}