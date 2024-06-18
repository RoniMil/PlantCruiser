package com.example.plantcruiser.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

// helper functions for type conversions
class HelperFunctions {
    companion object {
        fun fromBitmap(bitmap: Bitmap?): ByteArray? {
            if (bitmap == null) return null
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }

        fun toBitmap(byteArray: ByteArray?): Bitmap? {
            if (byteArray == null) return null
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }



    }


}