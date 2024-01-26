package com.example.sampleroomdatabase.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sampleroomdatabase.data.database.Contact.Companion.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "first_name")
    val firstName: String = "",
    val lastName: String = "",
    val avatarColor: Long,
    val mobileNumber: String = "",
    val email: String = "",
    val companyName: String = ""
) {
    companion object {
        const val DATABASE_NAME = "MyContactDatabase"
    }
}