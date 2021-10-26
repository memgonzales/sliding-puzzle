package com.gonzales.mark.n_puzzle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class UploadUtil {
    companion object {
        /**
         * Obtains the necessary permissions for choosing a photo from the Gallery. If these
         * permissions have already been granted beforehand, this method also defines the subsequent
         * behavior.
         *
         * To access the Gallery, a <code>READ_EXTERNAL_STORAGE</code> permission is requested
         * from the user.
         *
         * @param activity Activity calling this method.
         * @param galleryLauncher Activity result launcher related to choosing photos from the Gallery.
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
                    RequestCodes.REQUEST_CODE_POST_GALLERY.ordinal
                )
            } else {
                chooseFromGalleryIntent(galleryLauncher)
            }
        }

        /**
         * Launches the Gallery should the user decide to choose a photo from it.
         *
         * @param galleryLauncher Activity result launcher related to choosing photos from the Gallery.
         */
        private fun chooseFromGalleryIntent(galleryLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            galleryLauncher.launch(intent)
        }

        /**
         * Defines the behavior depending on whether the user granted (or denied) the necessary
         * permissions for choosing a photo from the Gallery.
         *
         * @param grantResults grant results for the corresponding permissions which is either <code>
         *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
         *     Never null
         * @param context context tied to the activity calling this method
         * @param galleryLauncher activity result launcher related to choosing photos from the Gallery
         */
        fun permissionsResultGallery(grantResults: IntArray, context: Context,
                                     galleryLauncher: ActivityResultLauncher<Intent>
        ) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseFromGalleryIntent(galleryLauncher)
            } else {
                Toast.makeText(
                    context,
                    "Insufficient permissions to access your gallery",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}