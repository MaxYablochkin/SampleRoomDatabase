package com.example.sampleroomdatabase

import android.app.Application
import com.example.sampleroomdatabase.data.database.ContactDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ContactApp : Application() {
    val database by lazy { ContactDatabase.createDatabase(this) }
}