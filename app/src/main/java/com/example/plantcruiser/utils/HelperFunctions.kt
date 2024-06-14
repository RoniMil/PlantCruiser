package com.example.plantcruiser.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.plantcruiser.R


class HelperFunctions {
    companion object {
        fun openImagePicker(
            fragment: Fragment,
        ) {
            val options = arrayOf<CharSequence>(
                "Take Photo",
                "Choose from Gallery",
                fragment.getString(R.string.dialog_cancel)
            )
            val builder = androidx.appcompat.app.AlertDialog.Builder(fragment.requireContext())
            builder.setTitle(fragment.getString(R.string.select_image))
            builder.setItems(options) { dialog, item ->
                when (options[item]) {
                    "Take Photo" -> askCameraPermission(fragment)
                    "Choose from Gallery" -> askGalleryPermission(fragment)
                    else -> dialog.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.show()
        }

        private fun askCameraPermission(fragment: Fragment) {
            if (ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    fragment.requireActivity(),
                    arrayOf<String>(Manifest.permission.CAMERA),
                    Constants.REQUEST_IMAGE_CAPTURE
                )
            } else {
                openCamera(fragment)
            }
        }

        private fun askGalleryPermission(fragment: Fragment) {
            if (ContextCompat.checkSelfPermission(
                    fragment.requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    fragment.requireActivity(),
                    arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.REQUEST_IMAGE_GALLERY
                )
            } else {
                openGallery(fragment)
            }
        }

        fun openCamera(fragment: Fragment) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            fragment.startActivityForResult(cameraIntent, Constants.REQUEST_IMAGE_CAPTURE)
        }

        fun openGallery(fragment: Fragment) {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            fragment.startActivityForResult(galleryIntent, Constants.REQUEST_IMAGE_GALLERY)
        }


    }


}