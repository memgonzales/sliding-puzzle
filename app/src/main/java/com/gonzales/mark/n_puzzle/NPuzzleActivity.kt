package com.gonzales.mark.n_puzzle

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class NPuzzleActivity : AppCompatActivity() {
    companion object {
        private const val NUM_COLUMNS = 3
        private const val NUM_TILES = NUM_COLUMNS * NUM_COLUMNS

        private const val BORDER_OFFSET = 6
        private const val BLANK_TILE_MARKER = NUM_TILES - 1
    }

    private lateinit var clRoot: ConstraintLayout
    private lateinit var gvgPuzzle: GridViewGesture

    private lateinit var btnUpload: Button
    private lateinit var btnShuffle: Button
    private lateinit var pbShuffle: ProgressBar

    private lateinit var tvMoveNumber: TextView
    private lateinit var tvFewestMoves: TextView
    private lateinit var tvTimeTaken: TextView
    private lateinit var tvFastestTime: TextView

    private lateinit var tvTitle: TextView
    private lateinit var tvSuccess: TextView
    private lateinit var tvTrivia: TextView

    private var tileDimen: Int = 0
    private var puzzleDimen: Int = 0

    private lateinit var imageChunks: ArrayList<Bitmap>
    private lateinit var blankImageChunks: ArrayList<Bitmap>
    private lateinit var tileImages: ArrayList<ImageButton>

    private lateinit var correctPuzzleState: ArrayList<Int>
    private lateinit var puzzleState: ArrayList<Int>
    private var blankTilePos: Int = BLANK_TILE_MARKER

    private lateinit var shuffleRunnable: ShuffleRunnable
    private lateinit var shuffleScheduler: ScheduledExecutorService
    private lateinit var shuffleHandler: Handler

    private var isPuzzleGridFrozen: Boolean = false
    private var isGameInSession: Boolean = false

    private var numMoves: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_puzzle)

        initComponents()
        initShuffleConcurrency()
        initStateAndTileImages()
        initPuzzle()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, clRoot).let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    /**************************
     * Initialization Methods *
     **************************/

    private fun initComponents() {
        clRoot = findViewById(R.id.cl_root)
        gvgPuzzle = findViewById(R.id.gvg_puzzle)

        btnUpload = findViewById(R.id.btn_upload)
        btnShuffle = findViewById(R.id.btn_shuffle)
        btnShuffle.setOnClickListener {
            if (!isGameInSession) {
                shuffle()
            } else {
                solve()
            }
        }

        pbShuffle = findViewById(R.id.pb_shuffle)

        tvMoveNumber = findViewById(R.id.tv_move_number)
        tvFewestMoves = findViewById(R.id.tv_fewest_moves)
        tvTimeTaken = findViewById(R.id.tv_time_taken)
        tvFastestTime = findViewById(R.id.tv_fastest_time)

        tvTitle = findViewById(R.id.tv_title)
        tvSuccess = findViewById(R.id.tv_success)
        tvSuccess.setOnClickListener {
            tvSuccess.visibility = View.GONE
        }
        tvTrivia = findViewById(R.id.tv_trivia)
    }

    private fun initShuffleConcurrency() {
        shuffleScheduler = Executors.newScheduledThreadPool(NUM_TILES)
        shuffleHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(message: Message) {
                super.handleMessage(message)

                showTileAt(message.data.getInt(Key.KEY_TILE_POSITION.name))
                pbShuffle.progress = message.data.getInt(Key.KEY_PROGRESS.name)
                updateComponents()
            }
        }
    }

    private fun initStateAndTileImages() {
        correctPuzzleState = ArrayList(NUM_TILES)
        puzzleState = ArrayList(NUM_TILES)
        tileImages = ArrayList(NUM_TILES)

        for (tile in 0 until NUM_TILES) {
            correctPuzzleState.add(tile)
            puzzleState.add(tile)
            tileImages.add(ImageButton(this))
        }
    }

    private fun initPuzzle() {
        setTouchSlopThreshold()
        setOnFlingListener()
        setDimensions()
    }

    /********************************************
     * Methods Related to Puzzle State and Grid *
     ********************************************/

    private fun setTouchSlopThreshold() {
        gvgPuzzle.setTouchSlopThreshold(ViewConfiguration.get(this).scaledTouchSlop)
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
        for ((position, tile) in puzzleState.withIndex()) {
            if (position == blankTilePos) {
                tileImages[blankTilePos].setImageBitmap(blankImageChunks[blankTilePos])
            } else {
                tileImages[position].setImageBitmap(imageChunks[tile])
            }
        }

        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    private fun displayBlankPuzzle() {
        for ((position, tile) in puzzleState.withIndex()) {
            if (position == blankTilePos) {
                tileImages[blankTilePos].setImageBitmap(blankImageChunks[blankTilePos])
            } else {
                tileImages[position].setImageBitmap(blankImageChunks[tile])
            }
        }

        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    /***********************************
     * Methods Related to Moving Tiles *
     ***********************************/

    private fun moveTile(direction: FlingDirection, position: Int) {
        if (!isPuzzleGridFrozen) {
            if (MoveUtil.canMoveTile(direction, position, blankTilePos, NUM_COLUMNS)) {
                /* Swap the flung tile and the blank tile via Kotlin's also idiom. */
                puzzleState[position] = puzzleState[blankTilePos].also {
                    puzzleState[blankTilePos] = puzzleState[position]
                    blankTilePos = position
                }

                displayPuzzle()

                if (isGameInSession) {
                    trackMove()

                    if (puzzleState == correctPuzzleState) {
                        prepareForNewGame(SolveStatus.USER_SOLVED)
                    }
                }
            }

            tvSuccess.visibility = View.GONE
        }
    }

    private fun trackMove() {
        numMoves++
        tvMoveNumber.text = numMoves.toString()
    }

    /********************************
     * Methods Related to Shuffling *
     ********************************/

    private fun shuffle() {
        pbShuffle.visibility = View.VISIBLE
        pbShuffle.progress = 0
        btnShuffle.text = getString(R.string.randomizing)

        btnUpload.visibility = View.INVISIBLE
        tvTrivia.visibility = View.VISIBLE
        tvSuccess.visibility = View.GONE

        disableClickables()
        resetGameStats()

        getValidShuffledState()
        displayBlankPuzzle()
        startShowingTiles()
    }

    private fun getValidShuffledState() {
        val shuffledState: Pair<ArrayList<Int>, Int> =
            ShuffleUtil.getValidShuffledState(puzzleState, BLANK_TILE_MARKER)

        puzzleState = shuffledState.first
        blankTilePos = shuffledState.second
    }

    private fun updateComponents() {
        when (pbShuffle.progress) {
            (NUM_TILES - 1) / 2 -> halfwayShuffling()
            (NUM_TILES - 1) -> finishShuffling()
        }
    }

    private fun halfwayShuffling() {
        btnShuffle.text = getString(R.string.inversions)
    }

    private fun finishShuffling() {
        tvTitle.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.btn_first_variant
            )
        )

        btnShuffle.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.btn_first_variant
            )
        )
        btnShuffle.text = getString(R.string.randomized)
        pbShuffle.visibility = View.GONE
        enableClickables()

        isGameInSession = true
    }

    private fun disableClickables() {
        isPuzzleGridFrozen = true
        btnShuffle.isEnabled = false
    }

    private fun enableClickables() {
        isPuzzleGridFrozen = false
        btnShuffle.isEnabled = true
    }

    private fun showTileAt(position: Int) {
        tileImages[position].setImageBitmap(imageChunks[puzzleState[position]])
        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    private fun startShowingTiles() {
        for (position in 0 until tileImages.size) {
            if (position != blankTilePos) {
                val delay: Long =
                    ((0..AnimationUtil.SHUFFLING_ANIMATION_UPPER_BOUND).random()
                            + AnimationUtil.SHUFFLING_ANIMATION_OFFSET).toLong()

                shuffleRunnable = ShuffleRunnable(shuffleHandler, position, NUM_TILES)
                shuffleScheduler.schedule(shuffleRunnable, delay, TimeUnit.MILLISECONDS)
            }
        }
    }

    private fun resetGameStats() {
        numMoves = 0
        tvMoveNumber.text = numMoves.toString()
    }

    /******************************
     * Methods Related to Solving *
     ******************************/

    private fun solve() {
        prepareForNewGame(SolveStatus.COMPUTER_SOLVED)
    }

    private fun prepareForNewGame(solveStatus: SolveStatus) {
        displaySuccessMessage(solveStatus)
        tvTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.btn_first))

        btnShuffle.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.btn_first
            )
        )
        btnShuffle.text = getString(R.string.btn_shuffle)

        btnUpload.visibility = View.VISIBLE
        tvTrivia.visibility = View.GONE

        updateGameStats()
    }

    private fun displaySuccessMessage(solveStatus: SolveStatus) {
        tvSuccess.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            tvSuccess.visibility = View.GONE
        }, AnimationUtil.SUCCESS_DISPLAY.toLong())
    }

    private fun updateGameStats() {
        isGameInSession = false
    }
}