package com.example.sampleroomdatabase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sampleroomdatabase.data.Contact
import com.example.sampleroomdatabase.data.ContactDatabase
import kotlinx.coroutines.launch

class ContactViewModel(
    private val database: ContactDatabase
) : ViewModel() {
    private var contactEntity: Contact? = null

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")

    val allContacts = database.contactDao.getAllContacts()

    fun insertContact() = viewModelScope.launch {
        val contact = contactEntity?.copy(firstName = firstName, lastName = lastName) ?: Contact(
            firstName = firstName,
            lastName = lastName
        )
        database.contactDao.insertContact(contact)
        contactEntity = null
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        database.contactDao.deleteContact(contact)
    }

    companion object {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as ContactApp).database
                return ContactViewModel(database) as T
            }
        }
    }
}
