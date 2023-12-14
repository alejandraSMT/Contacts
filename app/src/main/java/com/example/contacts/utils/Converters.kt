package com.example.contacts.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class Converters {
    @TypeConverter
    fun base64ToBitmap(base64String: String):Bitmap{
        //convert Base64 String into byteArray
        val byteArray = Base64.decode(base64String, DEFAULT_BUFFER_SIZE)
        //byteArray to Bitmap
        return BitmapFactory.decodeByteArray(byteArray,
            0, byteArray.size)
    }

    fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}