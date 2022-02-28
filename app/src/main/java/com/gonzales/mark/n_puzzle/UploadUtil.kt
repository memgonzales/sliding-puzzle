package com.gonzales.mark.n_puzzle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Utility class providing methods for choosing a photo from the Gallery as the custom puzzle image.
 *
 * @constructor Creates an object that provides methods for choosing a photo from the Gallery as the custom puzzle image.
 */
class UploadUtil {
    /**
     * Companion object containing the methods for choosing a photo from the Gallery as the custom
     * puzzle image.
     */
    companion object {
        /**
         * Obtains the necessary permission for choosing an image from the Gallery. If this
         * permission have already been granted beforehand, this method also defines the subsequent
         * behavior.
         *
         * To access the Gallery, a <code>READ_EXTERNAL_STORAGE</code> permission is requested
         * from the user.
         *
         * @param activity Activity calling this method.
         * @param galleryLauncher Activity result launcher related to choosing an image from the Gallery.
         */
        fun chooseFromGallery(activity: Activity, galleryLauncher: ActivityResultLauncher<Intent>) {
            val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            if (ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    permissions[0]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    RequestCode.REQUEST_CODE_IMAGE_GALLERY.ordinal
                )
            } else {
                chooseFromGalleryIntent(galleryLauncher)
            }
        }

        /**
         * Launches the Gallery should the user decide to choose an image from it.
         *
         * @param galleryLauncher Activity result launcher related to choosing an image from the Gallery.
         */
        private fun chooseFromGalleryIntent(galleryLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            galleryLauncher.launch(intent)
        }

        /**
         * Defines the behavior depending on whether the user granted (or denied) the necessary
         * permission for choosing an image from the Gallery.
         *
         * @param grantResults Grant results for the corresponding permissions which is either <code>
         *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
         *     Never null.
         * @param context Context tied to the activity calling this method.
         * @param galleryLauncher Activity result launcher related to choosing an image from the Gallery.
         */
        fun permissionsResultGallery(
            grantResults: IntArray, context: Context,
            galleryLauncher: ActivityResultLauncher<Intent>
        ) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseFromGalleryIntent(galleryLauncher)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}