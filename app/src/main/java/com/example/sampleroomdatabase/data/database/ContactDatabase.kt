package com.example.sampleroomdatabase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sampleroomdatabase.data.database.Contact.Companion.DATABASE_NAME

@Database(entities = [Contact::class], version = 5)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao

    companion object {
        fun createDatabase(context: Context): ContactDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = ContactDatabase::class.java,
                name = DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}