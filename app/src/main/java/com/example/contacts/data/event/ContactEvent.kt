package com.example.contacts.data.event

import com.example.contacts.model.Contact

sealed interface ContactEvent {
    object SaveContact : ContactEvent
    data class setName(val name: String) : ContactEvent
    data class setPhone(val number: String) : ContactEvent
    object setFavorite : ContactEvent
    object ShowDialog : ContactEvent
    object HideDialog : ContactEvent
    data class DeleteContact(val contact: Contact) : ContactEvent
}