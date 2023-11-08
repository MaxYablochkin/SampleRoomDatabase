package com.example.sampleroomdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ContactsDB", ignoredColumns = ["companyName", "mobileNumber", "email"])
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "first_name") val firstName: String,
    val lastName: String
) : ShowMoreInfoForContact()

open class ShowMoreInfoForContact {
    val companyName: String? = null
    val mobileNumber: String? = null
    val email: String? = null
}