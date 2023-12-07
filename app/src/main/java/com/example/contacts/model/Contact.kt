package com.example.contacts.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val name: String,
    val number: String,
    val favorite: Boolean?=false
)
