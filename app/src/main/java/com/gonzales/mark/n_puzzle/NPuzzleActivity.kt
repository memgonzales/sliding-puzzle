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

class NPuzzleActivity : AppCompatActivity() {
    companion object {
        const val MAX_NUM_NEIGHBORS = 4
        const val BORDER_OFFSET = 6
        const val PLACEHOLDER_ID = R.drawable.placeholder

        const val NO_TILE = -1
        const val INIT_BLANK_TILE_POS = PuzzleUtil.NUM_TILES - 1
    }

    private lateinit var clRoot: ConstraintLayout
    private lateinit var gvgPuzzle: GridViewGesture
    private lateinit var btnShuffle: Button
    private lateinit var pbShuffle: ProgressBar

    private var tileDimen: Int = 0
    private var puzzleDimen: Int = 0

    private lateinit var imageChunks: ArrayList<Bitmap>
    private lateinit var tileImages: ArrayList<ImageButton>
    private lateinit var placeholder: Bitmap

    private lateinit var puzzleState: ArrayList<Int>
    private var blankTilePos: Int = INIT_BLANK_TILE_POS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_puzzle)

        initComponents()
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

    private fun initStateAndTileImages() {
        puzzleState = ArrayList(PuzzleUtil.NUM_TILES)
        tileImages = ArrayList(PuzzleUtil.NUM_TILES)

        for (i in 0 until PuzzleUtil.NUM_TILES) {
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
                tileDimen = puzzleDimen / PuzzleUtil.NUM_COLUMNS

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

        placeholder = ImageUtil.resizeToBitmap(
            ImageUtil.drawableToBitmap(this@NPuzzleActivity, PLACEHOLDER_ID),
            tileDimen - BORDER_OFFSET,
            tileDimen - BORDER_OFFSET
        )

        imageChunks = ImageUtil.splitBitmap(image, tileDimen - BORDER_OFFSET)
        imageChunks[imageChunks.size - 1] = placeholder

        displayPuzzle()
    }

    private fun displayPuzzle() {
        for ((i, tile) in puzzleState.withIndex()) {
            tileImages[i].setImageBitmap(imageChunks[tile])
        }

        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    private fun moveTile(direction: FlingDirection, position: Int) {
        if (canMoveTile(direction, position)) {
            /* Swap the flung tile and the blank tile via Kotlin's also idiom. */
            puzzleState[position] = puzzleState[blankTilePos].also {
                puzzleState[blankTilePos] = puzzleState[position]
                blankTilePos = position
            }

            displayPuzzle()
        }
    }

    private fun canMoveTile(direction: FlingDirection, position: Int): Boolean {
        return Pair(direction, position) in getValidFlings(getNeighborsBlank())
    }

    private fun getValidFlings(neighbors: ArrayList<Int>): ArrayList<Pair<FlingDirection, Int>> {
        val validFlings = ArrayList<Pair<FlingDirection, Int>>(MAX_NUM_NEIGHBORS)
        val directions = FlingDirection.values()

        for (fling in directions.zip(neighbors)) {
            validFlings.add(Pair(fling.component1(), fling.component2()))
        }

        return validFlings
    }

    private fun getNeighborsBlank(): ArrayList<Int> {
        val neighbors = ArrayList<Int>(MAX_NUM_NEIGHBORS)

        /*
         * The order of addition into the neighbors ArrayList is important in the implementation
         * of getValidFlings().
         *
         * The diametrically opposite direction is added following the order of elements
         *     in the FlingDirection enumeration class. To illustrate: since the first
         *     element in the FlingDirection enumeration class is UP, the first neighbor
         *     added is the bottom one.
         */

        /* Bottom neighbor */
        neighbors.add(blankTilePos + PuzzleUtil.NUM_COLUMNS)

        /* Top neighbor */
        neighbors.add(blankTilePos - PuzzleUtil.NUM_COLUMNS)

        /* Right neighbor */
        if (!isRightEdgeTile(blankTilePos)) {
            neighbors.add(blankTilePos + 1)
        } else {
            neighbors.add(NO_TILE)
        }

        /* Left neighbor */
        if (!isLeftEdgeTile(blankTilePos)) {
            neighbors.add(blankTilePos - 1)
        } else {
            neighbors.add(NO_TILE)
        }

        return neighbors
    }

    private fun isLeftEdgeTile(position: Int): Boolean {
        return position % PuzzleUtil.NUM_COLUMNS == 0
    }

    private fun isRightEdgeTile(position: Int): Boolean {
        return position % PuzzleUtil.NUM_COLUMNS == PuzzleUtil.NUM_COLUMNS - 1
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun shuffle() {
        pbShuffle.visibility = View.VISIBLE
        btnShuffle.text = getString(R.string.randomizing)

        clearPuzzleGrid()
        getValidShuffledState()
        displayPuzzle()
    }

    private fun clearPuzzleGrid() {
        for (i in 0 until PuzzleUtil.NUM_TILES) {
            tileImages[i].setImageBitmap(placeholder)
        }
    }

    private fun getValidShuffledState() {
        puzzleState.shuffle()


        blankTilePos = puzzleState.indexOf(INIT_BLANK_TILE_POS)
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