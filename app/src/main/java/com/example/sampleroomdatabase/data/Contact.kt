package com.example.sampleroomdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sampleroomdatabase.data.Contact.Companion.DATABASE_NAME

@Entity(tableName = DATABASE_NAME, ignoredColumns = ["companyName", "mobileNumber", "email"])
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "first_name") val firstName: String,
    val lastName: String
) : ShowMoreInfoForContact() {
    companion object {
        const val DATABASE_NAME = "MyContactDatabase"
    }
}

open class ShowMoreInfoForContact {
    val companyName: String? = null
    val mobileNumber: String? = null
    val email: String? = null
}