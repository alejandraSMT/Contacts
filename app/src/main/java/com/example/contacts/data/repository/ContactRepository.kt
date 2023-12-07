package com.example.contacts.data.repository

import androidx.lifecycle.LiveData
import com.example.contacts.data.dao.ContactDao
import com.example.contacts.model.Contact

class ContactRepository(
    private val contactDao : ContactDao
) {
    val allContacts: LiveData<List<Contact>> = contactDao.allContacts()

    suspend fun insert(contact: Contact){
        contactDao.insertContact(contact)
    }

    suspend fun delete(contact: Contact){
        contactDao.deleteContact(contact)
    }

    suspend fun update(contact: Contact){
        contactDao.addToFavorite(contact)
    }

}