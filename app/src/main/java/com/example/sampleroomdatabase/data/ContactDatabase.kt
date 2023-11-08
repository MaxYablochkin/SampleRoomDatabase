package com.example.sampleroomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao

    companion object {
        fun createDatabase(context: Context) = Room.databaseBuilder(
            context = context,
            klass = ContactDatabase::class.java,
            name = "MyContactsDatabase.db"
        ).build()
    }
}
