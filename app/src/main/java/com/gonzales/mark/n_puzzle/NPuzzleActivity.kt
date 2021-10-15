package com.gonzales.mark.n_puzzle

import android.content.Context
import android.content.SharedPreferences
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

        private const val DEFAULT_FEWEST_MOVES = Long.MAX_VALUE
        private const val DEFAULT_FASTEST_TIME = Long.MAX_VALUE
    }

    /***************************
     * View-Related Properties *
     ***************************/

    private lateinit var clRoot: ConstraintLayout
    private lateinit var gvgPuzzle: GridViewGesture

    private lateinit var btnUpload: Button
    private lateinit var btnShuffle: Button
    private lateinit var pbShuffle: ProgressBar

    private lateinit var tvMoveNumber: TextView
    private lateinit var tvFewestMoves: TextView
    private lateinit var tvTimeTaken: TextView
    private lateinit var tvFastestTime: TextView

    /**
     * Text view for the title of the app.
     */
    private lateinit var tvTitle: TextView

    /**
     * Text view for the success message.
     */
    private lateinit var tvSuccess: TextView

    /**
     * Text view for the trivia.
     */
    private lateinit var tvTrivia: TextView

    /**********************
     * Shared Preferences *
     **********************/

    private lateinit var sp: SharedPreferences

    /***************
     * Dimensions *
     **************/

    private var tileDimen: Int = 0
    private var puzzleDimen: Int = 0

    /*********
     * State *
     *********/

    private lateinit var goalPuzzleState: ArrayList<Int>
    private lateinit var puzzleState: ArrayList<Int>
    private var blankTilePos: Int = BLANK_TILE_MARKER

    private var isPuzzleGridFrozen: Boolean = false
    private var isGameInSession: Boolean = false

    /**********
     * Images *
     **********/

    private lateinit var imageChunks: ArrayList<Bitmap>
    private lateinit var blankImageChunks: ArrayList<Bitmap>
    private lateinit var tileImages: ArrayList<ImageButton>

    /********************************
     * Shuffling-Related Properties *
     ********************************/

    private lateinit var shuffleRunnable: ShuffleRunnable
    private lateinit var shuffleScheduler: ScheduledExecutorService
    private lateinit var shuffleHandler: Handler

    /****************************
     * Timer-Related Properties *
     ****************************/

    private lateinit var timerHandler: Handler
    private var isTimerRunning: Boolean = false

    /**************
     * Statistics *
     **************/

    private var numMoves: Long = 0
    private var fewestMoves: Long = DEFAULT_FEWEST_MOVES

    private var timeTaken: Long = 0
    private var fastestTime: Long = DEFAULT_FASTEST_TIME

    /******************************
     * Solving-Related Properties *
     ******************************/

    private var puzzleSolution: Stack<ArrayList<Int>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_puzzle)

        /* Initialize all the necessary components, properties, etc. */
        initComponents()
        initSharedPreferences()
        initHandlers()
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
        /* Initialize the layout and grid view. */
        clRoot = findViewById(R.id.cl_root)
        gvgPuzzle = findViewById(R.id.gvg_puzzle)

        /* Initialize the buttons. */
        btnUpload = findViewById(R.id.btn_upload)
        btnShuffle = findViewById(R.id.btn_shuffle)
        btnShuffle.setOnClickListener {
            if (!isGameInSession) {
                shuffle()
            } else {
                solve()
            }
        }

        /* Initialize the progress bar. */
        pbShuffle = findViewById(R.id.pb_shuffle)

        /* Initialize the text views. */
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

    private fun initSharedPreferences() {
        sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        /*
         * Retrieve data on the fewest moves and fastest time.
         * These data are stored as strings to prevent problems related to integer overflow.
         */
        fewestMoves =
            sp.getString(Key.KEY_FEWEST_MOVES.name, DEFAULT_FEWEST_MOVES.toString())?.toLong()
                ?: DEFAULT_FEWEST_MOVES
        fastestTime =
            sp.getString(Key.KEY_FASTEST_TIME.name, DEFAULT_FASTEST_TIME.toString())?.toLong()
                ?: DEFAULT_FASTEST_TIME

        displayStats()
    }

    private fun initHandlers() {
        /* Initialize thread pool executor and handler related to the shuffling animation. */
        shuffleScheduler = Executors.newScheduledThreadPool(NUM_TILES)
        shuffleHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(message: Message) {
                super.handleMessage(message)

                /* Animate UI elements while shuffling. */
                showTileAt(message.data.getInt(Key.KEY_TILE_POSITION.name))
                pbShuffle.progress = message.data.getInt(Key.KEY_PROGRESS.name)
                updateComponents()
            }
        }

        /* Initialize the handler related to the timer. */
        timerHandler = Handler(Looper.getMainLooper())
    }

    private fun initStateAndTileImages() {
        goalPuzzleState = ArrayList(NUM_TILES)
        puzzleState = ArrayList(NUM_TILES)
        tileImages = ArrayList(NUM_TILES)

        for (tile in 0 until NUM_TILES) {
            goalPuzzleState.add(tile)
            puzzleState.add(tile)

            /*
             * Initialize at activity creation to prevent potentially expensive creation
             * of image button objects. Only the background resources of these image buttons
             * are changed when the tiles are moved.
             */
            tileImages.add(ImageButton(this))
        }
    }

    private fun initPuzzle() {
        setTouchSlopThreshold()
        setOnFlingListener()
        setDimensions()
    }

    /***********************************************
     * Methods Related to Initial Display of Stats *
     ***********************************************/

    private fun displayStats() {
        displayFewestMoves()
        displayFastestTime()
    }

    private fun displayFewestMoves() {
        tvFewestMoves.text = if (fewestMoves == DEFAULT_FEWEST_MOVES) {
            getString(R.string.default_fewest)
        } else {
            fewestMoves.toString()
        }
    }

    private fun displayFastestTime() {
        tvFastestTime.text = if (fastestTime == DEFAULT_FASTEST_TIME) {
            getString(R.string.default_fastest)
        } else {
            TimeUtil.displayTime(fastestTime)
        }
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

                /* Calculate the side length of each square tile. */
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

        /* Store copies of the tiles, alongside versions with dark filter applied (blank tiles). */
        imageChunks =
            ImageUtil.splitBitmap(image, tileDimen - BORDER_OFFSET, NUM_TILES, NUM_COLUMNS).first
        blankImageChunks =
            ImageUtil.splitBitmap(image, tileDimen - BORDER_OFFSET, NUM_TILES, NUM_COLUMNS).second

        displayPuzzle()
    }

    private fun displayPuzzle() {
        /*
         * Once this loop finished executing, there should be 9 distinct image chunks:
         * 8 tiles with no filter applied and 1 tile with dark filter applied (blank tile).
         */
        for ((position, tile) in puzzleState.withIndex()) {
            /* Properly reflect the blank tile depending on the puzzle state. */
            if (position == blankTilePos) {
                tileImages[blankTilePos].setImageBitmap(blankImageChunks[blankTilePos])
            } else {
                tileImages[position].setImageBitmap(imageChunks[tile])
            }
        }

        /* Set (or reset) the adapter of the grid view. */
        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    private fun displayBlankPuzzle() {
        /*
         * Once this loop finished executing, there should be 9 image chunks, all of which have
         * dark filter applied. However, among these chunks, there should be be a tile that appears
         * twice depending on which tile is the actual blank tile.
         */
        for ((position, tile) in puzzleState.withIndex()) {
            /* Properly reflect the blank tile depending on the puzzle state. */
            if (position == blankTilePos) {
                tileImages[blankTilePos].setImageBitmap(blankImageChunks[blankTilePos])
            } else {
                tileImages[position].setImageBitmap(blankImageChunks[tile])
            }
        }

        /* Set (or reset) the adapter of the grid view. */
        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    /***********************************
     * Methods Related to Moving Tiles *
     ***********************************/

    private fun moveTile(direction: FlingDirection, position: Int) {
        /* Use a flag to keep track of whether the success message can be removed. */
        var flag = false

        /* Move a tile only when the puzzle grid is clickable. */
        if (!isPuzzleGridFrozen) {
            if (MoveUtil.canMoveTile(direction, position, blankTilePos, NUM_COLUMNS)) {
                /*
                 * Swap the flung tile and the blank tile via Kotlin's also idiom.
                 * This creates an effect akin to tile sliding.
                 */
                puzzleState[position] = puzzleState[blankTilePos].also {
                    puzzleState[blankTilePos] = puzzleState[position]
                    blankTilePos = position
                }

                /* Update the grid and the statistics. */
                displayPuzzle()
                flag = updateGameStatus()

                /* Launch the timer after the first move. */
                if (numMoves == 1L) {
                    launchTimer()
                }
            }

            /*
             * Remove the success message (if it is displayed). This scenario is triggered when
             * the user moves a tile while the success message displayed is still displayed, right
             * after completing a game.
             */
            if (!flag) {
                tvSuccess.visibility = View.GONE
            }
        }
    }

    private fun updateGameStatus(): Boolean {
        if (isGameInSession) {
            trackMove()

            /* Check if the puzzle has been solved. */
            if (SolveUtil.isSolved(puzzleState, goalPuzzleState)) {
                /*
                 * Decrement to counter the effect of the last post-increment operation
                 * before the timer was stopped.
                 */
                timeTaken--

                if (numMoves < fewestMoves && timeTaken < fastestTime) {
                    endGame(SolveStatus.FEWEST_AND_FASTEST)
                } else if (numMoves < fewestMoves) {
                    endGame(SolveStatus.FEWEST_MOVES)
                } else if (timeTaken < fastestTime) {
                    endGame(SolveStatus.FASTEST_TIME)
                } else {
                    endGame(SolveStatus.USER_SOLVED)
                }

                return true
            }
        }

        return false
    }

    private fun trackMove() {
        numMoves++
        tvMoveNumber.text = numMoves.toString()
    }

    private fun launchTimer() {
        isTimerRunning = true

        timerHandler.post(object : Runnable {
            override fun run() {
                if (isTimerRunning) {
                    tvTimeTaken.text = TimeUtil.displayTime(timeTaken++)
                    timerHandler.postDelayed(this, TimeUtil.SECONDS_TO_MILLISECONDS.toLong())
                } else {
                    timerHandler.removeCallbacks(this)
                }
            }
        })
    }

    /********************************
     * Methods Related to Shuffling *
     ********************************/

    private fun shuffle() {
        /* Display the progress bar, and update the message displayed */
        pbShuffle.visibility = View.VISIBLE
        pbShuffle.progress = 0
        btnShuffle.text = getString(R.string.randomizing)

        /* Display trivia in place of the upload button. */
        btnUpload.visibility = View.INVISIBLE
        tvTrivia.visibility = View.VISIBLE

        /*
         * Handle the case when the shuffle button is clicked while the success message
         * is still displayed.
         */
        tvSuccess.visibility = View.GONE

        /* During shuffling, no UI element should be clickable. */
        disableClickables()

        /* Reset the displayed move number and time taken. */
        resetDisplayedStats()

        /*
         * Generate the shuffled state and apply dark filter to all the tiles before starting
         * the animation.
         */
        getValidShuffledState()

        /* Apply dark filter to all the tiles before starting the animation. */
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
        /* Signal the start of a new game. */
        isGameInSession = true

        /*
         * Change the colors of the game title and solve button, as well as the text displayed,
         * to visually indicate the start of a new game.
         */
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

        /* Remove the progress bar, and re-enable interaction with UI elements. */
        pbShuffle.visibility = View.GONE
        enableClickables()
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

        /* Set (or reset) the adapter of the grid view. */
        gvgPuzzle.adapter = TileAdapter(tileImages, tileDimen, tileDimen)
    }

    private fun startShowingTiles() {
        /* Concurrently show the tiles with randomized delay and order of appearance. */
        for (position in 0 until tileImages.size) {
            /* The blank tile should not be shown. */
            if (position != blankTilePos) {
                /* Randomize the delay. */
                val delay: Long =
                    ((0..AnimationUtil.SHUFFLING_ANIMATION_UPPER_BOUND).random()
                            + AnimationUtil.SHUFFLING_ANIMATION_OFFSET).toLong()

                /* Schedule the concurrent tasks of showing the tiles. */
                shuffleRunnable = ShuffleRunnable(shuffleHandler, position, NUM_TILES)
                shuffleScheduler.schedule(shuffleRunnable, delay, TimeUnit.MILLISECONDS)
            }
        }
    }

    private fun resetDisplayedStats() {
        /* Reset the statistics for the number of moves, and display them. */
        numMoves = 0
        tvMoveNumber.text = numMoves.toString()

        /* Reset the statistics for the time taken, and display them. */
        timeTaken = 0
        tvTimeTaken.text = TimeUtil.displayTime(timeTaken)
    }

    /***********************************************
     * Methods Related to Solving and Post-Solving *
     ***********************************************/

    private fun solve() {
        endGame(SolveStatus.COMPUTER_SOLVED)
        puzzleSolution = SolveUtil.solve(
            puzzleState,
            blankTilePos,
            goalPuzzleState,
            NUM_COLUMNS,
            BLANK_TILE_MARKER
        )
    }

    private fun endGame(solveStatus: SolveStatus) {
        /* Signal that the game is over. */
        isGameInSession = false
        isTimerRunning = false

        /* Save the updated statistics, and display them alongside the success message. */
        saveStats(solveStatus)
        displaySuccessMessage(solveStatus)

        if (solveStatus != SolveStatus.COMPUTER_SOLVED) {
            prepareForNewGame()
        } else {
            prepareForSolution()
        }
    }

    private fun prepareForNewGame() {
        /*
         * Revert the colors of the game title and solve button, as well as the text displayed,
         * to visually indicate the start of a new game.
         */
        tvTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.btn_first))

        btnShuffle.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.btn_first
            )
        )

        btnShuffle.text = getString(R.string.new_game)

        /* Revert the visibility of the upload button (instead of the trivia). */
        btnUpload.visibility = View.VISIBLE
        btnUpload.text = getString(R.string.upload_picture)
        tvTrivia.visibility = View.GONE
    }

    private fun prepareForSolution() {
        btnShuffle.text = getString(R.string.pause)

        btnUpload.visibility = View.VISIBLE
        btnUpload.text = getString(R.string.skip)
        tvTrivia.visibility = View.GONE
    }

    private fun saveStats(solveStatus: SolveStatus) {
        when (solveStatus) {
            SolveStatus.FEWEST_MOVES -> saveFewestMoves()
            SolveStatus.FASTEST_TIME -> saveFastestTime()
            SolveStatus.FEWEST_AND_FASTEST -> saveFewestAndFastest()
            else -> Unit
        }
    }

    private fun saveFewestAndFastest() {
        fewestMoves = numMoves
        tvFewestMoves.text = fewestMoves.toString()

        fastestTime = timeTaken
        tvFastestTime.text = TimeUtil.displayTime(fastestTime)

        /*
         * Store in the shared preferences file.
         * These data are stored as strings to prevent problems related to integer overflow.
         */
        with(sp.edit()) {
            putString(Key.KEY_FEWEST_MOVES.name, fewestMoves.toString())
            putString(Key.KEY_FASTEST_TIME.name, fastestTime.toString())
            apply()
        }
    }

    private fun saveFewestMoves() {
        fewestMoves = numMoves
        tvFewestMoves.text = fewestMoves.toString()

        /*
         * Store in the shared preferences file.
         * These data are stored as strings to prevent problems related to integer overflow.
         */
        with(sp.edit()) {
            putString(Key.KEY_FEWEST_MOVES.name, fewestMoves.toString())
            apply()
        }
    }

    private fun saveFastestTime() {
        fastestTime = timeTaken
        tvFastestTime.text = TimeUtil.displayTime(fastestTime)

        /*
         * Store in the shared preferences file.
         * These data are stored as strings to prevent problems related to integer overflow.
         */
        with(sp.edit()) {
            putString(Key.KEY_FASTEST_TIME.name, fastestTime.toString())
            apply()
        }
    }

    private fun displaySuccessMessage(solveStatus: SolveStatus) {
        /* Display a message depending on how the goal state of the puzzle was reached. */
        tvSuccess.visibility = View.VISIBLE
        tvSuccess.text = when (solveStatus) {
            SolveStatus.USER_SOLVED -> getString(R.string.user_solved)
            SolveStatus.FEWEST_MOVES, SolveStatus.FASTEST_TIME, SolveStatus.FEWEST_AND_FASTEST -> getString(
                R.string.high_score
            )
            SolveStatus.COMPUTER_SOLVED -> getString(R.string.computer_solved)
        }

        /* Make the success message disappear after a set number of seconds. */
        Handler(Looper.getMainLooper()).postDelayed({
            tvSuccess.visibility = View.GONE
        }, AnimationUtil.SUCCESS_DISPLAY.toLong())
    }
}