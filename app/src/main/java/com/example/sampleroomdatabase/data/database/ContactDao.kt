package com.example.sampleroomdatabase.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleroomdatabase.data.database.Contact.Companion.DATABASE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query(value = "SELECT * FROM $DATABASE_NAME")
    fun getAllContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
}