package com.example.sampleroomdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sampleroomdatabase.data.Contact.Companion.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    val lastName: String,
    val avatarColor: Long
) {
    companion object {
        const val DATABASE_NAME = "MyContactDatabase"
    }
}