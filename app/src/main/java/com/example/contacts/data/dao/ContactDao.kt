package com.example.contacts.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import androidx.lifecycle.LiveData
import com.example.contacts.model.Contact
import java.util.concurrent.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM Contact")
    fun allContacts() : LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE favorite='true'")
    suspend fun getFavorites() : LiveData<List<Contact>>

    @Update
    suspend fun addToFavorite(contact: Contact)

    @Update
    suspend fun deleteFromFavorite(contact: Contact)

}