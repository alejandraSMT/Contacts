package com.example.contacts.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.nio.ByteBuffer

class Converters {
    @TypeConverters
    fun bitmapToBase64(bitmap: Bitmap) : String{
        // create a ByteBuffer and allocate size equal to bytes in   the bitmap
        val byteBuffer = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
        //copy all the pixels from bitmap to byteBuffer
        bitmap.copyPixelsToBuffer(byteBuffer)
        //convert byte buffer into byteArray
        val byteArray = byteBuffer.array()
        //convert byteArray to Base64 String with default flags
        return Base64.encodeToString(byteArray, DEFAULT_BUFFER_SIZE)
    }

    @TypeConverter
    fun base64ToBitmap(base64String: String):Bitmap{
        //convert Base64 String into byteArray
        val byteArray = Base64.decode(base64String, DEFAULT_BUFFER_SIZE)
        //byteArray to Bitmap
        return BitmapFactory.decodeByteArray(byteArray,
            0, byteArray.size)
    }
}