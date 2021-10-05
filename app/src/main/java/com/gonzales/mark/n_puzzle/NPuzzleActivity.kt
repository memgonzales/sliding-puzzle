package com.gonzales.mark.n_puzzle

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class NPuzzleActivity : AppCompatActivity() {
    companion object {
        private const val NUM_COLUMNS = 3
        private const val NUM_TILES = NUM_COLUMNS * NUM_COLUMNS

        private const val BORDER_OFFSET = 6
        private const val BLANK_TILE_MARKER = NUM_TILES - 1
    }

    private lateinit var clRoot: ConstraintLayout
    private lateinit var gvgPuzzle: GridViewGesture
    private lateinit var btnShuffle: Button
    private lateinit var pbShuffle: ProgressBar

    private var tileDimen: Int = 0
    private var puzzleDimen: Int = 0

    private lateinit var imageChunks: ArrayList<Bitmap>
    private lateinit var blankImageChunks: ArrayList<Bitmap>
    private lateinit var tileImages: ArrayList<ImageButton>

    private lateinit var puzzleState: ArrayList<Int>
    private var blankTilePos: Int = BLANK_TILE_MARKER

    private lateinit var runnable: ShuffleRunnable
    private lateinit var scheduler: ScheduledExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_puzzle)

        initComponents()
        initScheduler()
        initStateAndTileImages()
        initPuzzle()
    }

    private fun initComponents() {
        clRoot = findViewById(R.id.cl_root)
        gvgPuzzle = findViewById(R.id.gvg_puzzle)

        btnShuffle = findViewById(R.id.btn_shuffle)
        btnShuffle.setOnClickListener {
            shuffle()
        }

        pbShuffle = findViewById(R.id.pb_shuffle)
    }

    private fun initScheduler() {
        scheduler = Executors.newScheduledThreadPool(NUM_TILES)
    }

    private fun initStateAndTileImages() {
        puzzleState = ArrayList(NUM_TILES)
        tileImages = ArrayList(NUM_TILES)

        for (i in 0 until NUM_TILES) {
            puzzleState.add(i)
            tileImages.add(ImageButton(this))
        }
    }

    private fun initPuzzle() {
        setTouchSlopThreshold()
        setOnFlingListener()
        setDimensions()
    }

    private fun setOnFlingListener() {
        gvgPuzzle.setFlingListener(object : OnFlingListener {
            override fun onFling(direction: FlingDirection, position: Int) {
                moveTile(direction, position)
            }
        })
    }

    private fun setDimensions() {
        gvgPuzzle.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                gvgPuzzle.viewTreeObserver.removeOnGlobalLayoutListener(this)
                puzzleDimen = gvgPuzzle.measuredWidth
                tileDimen = puzzleDimen / NUM_COLUMNS

                initChunks()
            }
        })
    }

    private fun setTouchSlopThreshold() {
        gvgPuzzle.setTouchSlopThreshold(ViewConfiguration.get(this).scaledTouchSlop)
    }

    private fun initChunks() {
        val image: Bitmap = ImageUtil.resizeToBitmap(
            ImageUtil.drawableToBitmap(this@NPuzzleActivity, R.drawable.shoob1),
            puzzleDimen, puzzleDimen
        )

        imageChunks =
            ImageUtil.splitBitmap(image, tileDimen - BORDER_OFFSET, NUM_TILES, NUM_COLUMNS).first
        blankImageChunks =
            ImageUtil.splitBitmap(image, tileDimen - BORDER_OFFSET, NUM_TILES, NUM_COLUMNS).second

        displayPuzzle()
    }

    private fun displayPuzzle() {
        for ((i, tile) in puzzleState.withIndex()) {
            if (i == blankTilePos) {
                tileImages[blankTilePos].setImageBitmap(blankImageChunks[blankTilePos])
            } else {
                tileImages[i].setImageBitmap(imageChunks[tile])
            }
        }

        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    private fun moveTile(direction: FlingDirection, position: Int) {
        if (MoveUtil.canMoveTile(direction, position, blankTilePos, NUM_COLUMNS)) {
            /* Swap the flung tile and the blank tile via Kotlin's also idiom. */
            puzzleState[position] = puzzleState[blankTilePos].also {
                puzzleState[blankTilePos] = puzzleState[position]
                blankTilePos = position
            }

            displayPuzzle()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun shuffle() {
        pbShuffle.visibility = View.VISIBLE
        pbShuffle.progress = 0
        btnShuffle.text = getString(R.string.randomizing)

        clearPuzzleGrid()
        getValidShuffledState()
        displayPuzzle()
    }

    private fun clearPuzzleGrid() {
//        for (i in 0 until NUM_TILES) {
//            tileImages[i].setImageBitmap(placeholder)
//        }
    }

    private fun getValidShuffledState() {
        val shuffledState: Pair<ArrayList<Int>, Int> =
            ShuffleUtil.getValidShuffledState(puzzleState, BLANK_TILE_MARKER)

        puzzleState = shuffledState.first
        blankTilePos = shuffledState.second
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, clRoot).let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}