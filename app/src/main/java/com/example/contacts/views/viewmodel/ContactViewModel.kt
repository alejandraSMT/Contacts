package com.example.contacts.views.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.data.dao.ContactDao
import com.example.contacts.data.dao.ContactDatabase
import com.example.contacts.data.event.ContactEvent
import com.example.contacts.data.repository.ContactRepository
import com.example.contacts.data.state.ContactState
import com.example.contacts.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) /*ViewModel()*/ {

    val allContacts : LiveData<List<Contact>>
    val repository : ContactRepository

    init {
        val dao = ContactDatabase.getDatabase(application).daoContact()
        repository = ContactRepository(dao)
        allContacts = repository.allContacts
    }

    fun addContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO){
            Log.i("ENTRO A ADD CONTACT: ", contact.toString())
            repository.insert(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(contact)
        }
    }

    /*private val _state = MutableStateFlow(ContactState())
    val contacts = dao.allContacts()
    val state = ContactState()
    /*val state = combine(_state,_contacts){ state, contacts ->
        state.copy(
            contacts = contacts
        )
    }*/

    fun onEvent(event: ContactEvent){
        when (event) {

            is ContactEvent.DeleteContact -> {
                viewModelScope.launch{
                    dao.deleteContact(event.contact)
                }
            }

            ContactEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingContact = false
                ) }
            }
            // if i want to sort contacts, i have to add a sort event with an enum
            ContactEvent.SaveContact -> {
                val name = _state.value.name
                val number = _state.value.number

                if(name.isBlank() || number.isBlank()){
                    return
                }

                val contact = Contact(
                    name = name,
                    number = number
                )

                viewModelScope.launch{
                    dao.insertContact(contact)
                }

                _state.update {it.copy(
                    isAddingContact = false,
                    name = "",
                    number = ""
                ) }
            }

            ContactEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                ) }
            }
            is ContactEvent.setName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is ContactEvent.setPhone -> {
                _state.update { it.copy(
                    number = event.number
                ) }
            }

            ContactEvent.setFavorite -> {

            }
        }
    }

    val contactsList = mutableListOf<Contact>()

    fun getContacts(context: Context){
        try{
            viewModelScope.launch {
                val daoContact: ContactDao = ContactDatabase.getDatabase(context)
                    .daoContact()
                val list = daoContact.allContacts()
                list.forEach {
                    contactsList.add(it)
                }
            }
        }catch(e:Exception){
            Log.e("ERROR GET_CONTACTS", e.message.toString())
        }

    }*/

}