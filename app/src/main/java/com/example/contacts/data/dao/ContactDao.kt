package com.example.contacts.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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
    fun getFavorites() : LiveData<List<Contact>>

    @Update
    suspend fun addToFavorite(contact: Contact)

    @Update
    suspend fun deleteFromFavorite(contact: Contact)

}