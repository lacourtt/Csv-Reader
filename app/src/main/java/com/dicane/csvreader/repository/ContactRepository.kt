package com.dicane.csvreader.repository

import android.content.Context
import com.dicane.csvreader.model.Contact
import kotlinx.coroutines.CoroutineScope

interface ContactRepository {
    suspend fun fetchFromDb() : List<Contact>
    suspend fun update(contact: Contact)
    suspend fun insertIntoDb(contact: Contact)
    suspend fun fetchFromCsv(context: Context, scope: CoroutineScope) : List<Contact>
}