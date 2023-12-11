package com.example.contacts.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val name: String,
    val number: String,
    val favorite: Boolean?=false,
    val contactImage : String?=null
)
