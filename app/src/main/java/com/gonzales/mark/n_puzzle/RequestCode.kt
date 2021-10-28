package com.gonzales.mark.n_puzzle

/**
 * Class containing the codes used in requesting permissions related to launching activities
 * related to the Gallery of the device.
 *
 * Since the said operations require integers as codes, they are referenced using their positions
 * in this enumeration declaration, that is, via the property <code>ordinal</code>.
 *
 * @constructor Creates an enumeration class containing the codes used in requesting permissions
 * related to launching activities related to the Gallery of the device.
 */
enum class RequestCode {
    /**
     * Request code for launching activities related to the Gallery.
     */
    REQUEST_CODE_IMAGE_GALLERY
}