package com.gonzales.mark.n_puzzle

/**
 * Enumeration class containing the puzzle images that come with the app.
 *
 * @constructor Creates an enumeration class containing the puzzle images that come with the app.
 * @param drawableId ID of the drawable corresponding to the puzzle image.
 */
enum class PuzzleImage(val drawableId: Int) {
    /**
     * Shiba Inu with stuffed toy:
     * https://www.facebook.com/groups/SillyShibas
     */
    LOBSTER_SHOOB(R.drawable.shoob1),

    /**
     * Eru Chitanda (from the anime/novel <i>Hyouka<i>):
     * https://www.crunchyroll.com/de/anime-feature/2018/06/11/love-is-the-greatest-mystery-of-all-in-hyouka
     */
    ROSE_COLORED_LIFE(R.drawable.chitanda),

    /**
     * Kikyo and Inuyasha (from the anime/manga <i>Inuyasha</i>):
     * https://inuyashacouples.fandom.com/wiki/InuKik
     */
    KIKYO_INUYASHA(R.drawable.kikyo1),

    /**
     * Yuko, Maru, and Moro (from the anime/manga <i>xxxHolic</i>):
     * https://static.zerochan.net/xxxHOLiC.full.1568772.jpg
     */
    YUKO_MARU_MORO(R.drawable.xxxholic1),

    /**
     * Enma Ai (from the anime <i>Jigoku Shoujo</i>):
     * https://recommendmeanime.com/anime-series-like-hell-girl/
     */
    JIGOKU_SHOUJO(R.drawable.jigoku_shoujo1),

    /**
     * Custom picture selected by the user from their Gallery.
     * The ID <code>-1</code> is only a dummy value.
     */
    CUSTOM(-1)
}