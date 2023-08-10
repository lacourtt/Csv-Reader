package com.dicane.csvreader.repository

import android.content.Context
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.database.ContactDao
import com.dicane.csvreader.datasource.CsvDatasource
import kotlinx.coroutines.CoroutineDispatcher

class ContactRepository(
    private val contactDao: ContactDao,
    private val csvDatasource: CsvDatasource
) {
    suspend fun fetchFromDb() = contactDao.getAllContacts()
    suspend fun update(contact: Contact) = contactDao.update(contact)
    suspend fun insertIntoDb(contact: Contact) = contactDao.insert(contact)
    suspend fun fetchFromCsv(context: Context, ioDispatcher: CoroutineDispatcher) = csvDatasource.fetchFromCsv(context, ioDispatcher)
}