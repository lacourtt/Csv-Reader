package com.dicane.csvreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val companyName: String,
    val address: String,
    val city: String,
    val county: String,
    val state: String,
    val zip: String,
    val phone1: String,
    val phone: String,
    val email: String,
)
