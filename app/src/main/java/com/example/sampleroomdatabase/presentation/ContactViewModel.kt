package com.example.sampleroomdatabase.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sampleroomdatabase.ContactApp
import com.example.sampleroomdatabase.R
import com.example.sampleroomdatabase.data.database.Contact
import com.example.sampleroomdatabase.data.database.ContactDatabase
import com.example.sampleroomdatabase.data.database.fakeContacts
import com.example.sampleroomdatabase.presentation.ui.components.selectRandomArgbColor
import kotlinx.coroutines.launch

class ContactViewModel(private val database: ContactDatabase) : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var mobileNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var companyName by mutableStateOf("")
    private var contactEntity: Contact? = null

    var selectedContacts: SnapshotStateList<Contact> = mutableStateListOf()

    val allContacts = database.contactDao.getAllContacts()

    private fun insertContact() = viewModelScope.launch {
        val contact = contactEntity?.copy(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            mobileNumber = mobileNumber.trim(),
            email = email.trim(),
            companyName = companyName.trim(),
            avatarColor = selectRandomArgbColor()
        ) ?: Contact(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            mobileNumber = mobileNumber.trim(),
            email = email.trim(),
            companyName = companyName.trim(),
            avatarColor = selectRandomArgbColor()
        )
        database.contactDao.insertContact(contact)
        contactEntity = null
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        database.contactDao.deleteContact(contact)
    }

    fun saveContact(context: Context, popBackStack: () -> Unit) {
        if (firstName.isBlank() && lastName.isBlank()) {
            Toast.makeText(context, R.string.toast_message, Toast.LENGTH_SHORT).show()
        } else {
            insertContact()
            popBackStack()
        }
    }

    fun clearSelectedContacts() = selectedContacts.removeAll(selectedContacts)

    fun deleteSelectedContacts(contacts: List<Contact>) = viewModelScope.launch {
        contacts.forEach { contact ->
            selectedContacts.forEach { selectedContact ->
                if (contact == selectedContact) {
                    deleteContact(contact)
                    Log.d("REMOVE", "$contact")
                }
            }
        }
        clearSelectedContacts()
    }

    fun preInitContacts() = viewModelScope.launch {
        fakeContacts.forEach {
            val preInitContact = it.copy(
                firstName = it.firstName.trim(),
                lastName = it.lastName.trim(),
                mobileNumber = it.mobileNumber.trim(),
                email = it.email.trim(),
                companyName = it.companyName.trim()
            )
            database.contactDao.insertContact(preInitContact)
        }
    }

    fun deleteAllContacts(contacts: List<Contact>) = viewModelScope.launch {
        contacts.forEach { contact ->
            database.contactDao.deleteContact(contact)
        }
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
