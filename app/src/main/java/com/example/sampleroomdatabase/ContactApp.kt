package com.example.sampleroomdatabase

import android.app.Application
import com.example.sampleroomdatabase.data.ContactDatabase

class ContactApp : Application() {
    val database by lazy { ContactDatabase.createDatabase(this) }
}