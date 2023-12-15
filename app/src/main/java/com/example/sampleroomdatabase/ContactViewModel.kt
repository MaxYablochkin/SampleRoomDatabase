package com.example.sampleroomdatabase

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

class ContactViewModel(private val database: ContactDatabase) : ViewModel() {
    var dynamicColor by mutableStateOf(false)
    var amoledColor by mutableStateOf(false)
    var enabledSwitchDynamicColor by mutableStateOf(true)
    var enabledSwitchAmoledColor by mutableStateOf(true)

    var selectedContacts: MutableList<Contact> = mutableStateListOf()
    var showToolsTopAppBar by mutableStateOf(false)

    private var contactEntity: Contact? = null
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")

    fun dynamicOrAmoled(isDark: Boolean) {
        when {
            dynamicColor -> {
                amoledColor = false
                enabledSwitchAmoledColor = false
            }
            !isDark -> {
                enabledSwitchAmoledColor = false
                amoledColor = false
            }
            else -> enabledSwitchAmoledColor = true
        }
        when {
            amoledColor -> {
                dynamicColor = false
                enabledSwitchDynamicColor = false
            }
            !isDark -> {
                enabledSwitchAmoledColor = false
                amoledColor = false
            }
            else -> enabledSwitchDynamicColor = true
        }
    }

    val allContacts = database.contactDao.getAllContacts()

    private fun insertContact() = viewModelScope.launch {
        val contact = contactEntity?.copy(firstName = firstName, lastName = lastName) ?: Contact(
            firstName = firstName,
            lastName = lastName
        )
        database.contactDao.insertContact(contact)
        contactEntity = null
    }

    private fun deleteContact(contact: Contact) = viewModelScope.launch {
        database.contactDao.deleteContact(contact)
    }

    fun saveContact(context: Context, popBackStack: () -> Unit) {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            Toast.makeText(context, "All fields empty", Toast.LENGTH_SHORT).show()
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
    }

    fun preInitContacts() = viewModelScope.launch {
        preInitContacts.forEach { preInitContact ->
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

        private val preInitContacts: List<Contact> = listOf(
            Contact(id = 1, firstName = "John", lastName = "Smith"),
            Contact(id = 2, firstName = "Alice", lastName = "Johnson"),
            Contact(id = 3, firstName = "Bob", lastName = "Williams"),
            Contact(id = 4, firstName = "Eva", lastName = "Jones"),
            Contact(id = 5, firstName = "David", lastName = "Brown"),
            Contact(id = 6, firstName = "Olivia", lastName = "Davis"),
            Contact(id = 7, firstName = "Michael", lastName = "Miller"),
            Contact(id = 8, firstName = "Sophia", lastName = "Wilson"),
            Contact(id = 9, firstName = "James", lastName = "Moore"),
            Contact(id = 10, firstName = "Emma", lastName = "Taylor"),
            Contact(id = 11, firstName = "Daniel", lastName = "Anderson"),
            Contact(id = 12, firstName = "Ava", lastName = "Thomas"),
            Contact(id = 13, firstName = "Matthew", lastName = "Jackson"),
            Contact(id = 14, firstName = "Isabella", lastName = "White"),
            Contact(id = 15, firstName = "Andrew", lastName = "Harris"),
            Contact(id = 16, firstName = "Emily", lastName = "Martin"),
            Contact(id = 17, firstName = "Christopher", lastName = "Thompson"),
            Contact(id = 18, firstName = "Mia", lastName = "Garcia"),
            Contact(id = 19, firstName = "Joshua", lastName = "Martinez"),
            Contact(id = 20, firstName = "Amelia", lastName = "Robinson")
        )
    }
}