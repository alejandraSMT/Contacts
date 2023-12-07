package com.example.contacts.data.state

import androidx.lifecycle.LiveData
import com.example.contacts.model.Contact

data class ContactState (
    val contacts : List<Contact> = emptyList(),
    val name: String = "",
    val number: String = "",
    val isAddingContact : Boolean = false
 )