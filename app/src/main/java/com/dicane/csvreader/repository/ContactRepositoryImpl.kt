package com.dicane.csvreader.repository

import android.content.Context
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.database.ContactDao
import com.dicane.csvreader.datasource.CsvDatasource
import kotlinx.coroutines.CoroutineScope

open class ContactRepositoryImpl(
    private val contactDao: ContactDao,
    private val csvDatasource: CsvDatasource
) : ContactRepository {
    override suspend fun fetchFromDb(): List<Contact> {
        return contactDao.getAllContacts()
    }

    override suspend fun update(contact: Contact) {
        return contactDao.update(contact)
    }

    override suspend fun insertIntoDb(contact: Contact) {
        return contactDao.insert(contact)
    }

    override suspend fun fetchFromCsv(context: Context, scope: CoroutineScope): List<Contact> {
        return csvDatasource.fetchFromCsv(context, scope)
    }
}