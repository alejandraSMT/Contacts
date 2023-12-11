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

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    fun allContacts() : LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE favorite='true'")
    fun getFavorites() : LiveData<List<Contact>>

    @Query("UPDATE Contact SET favorite='1' WHERE id=:idContact")
    fun addToFavorite(idContact: Int)

    @Query("UPDATE Contact SET favorite='0' WHERE id=:idContact")
    fun deleteFromFavorite(idContact: Int)

}