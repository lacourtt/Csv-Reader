package com.dicane.csvreader.usecase

import android.content.Context
import com.dicane.csvreader.MockUtil
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.repository.ContactRepository
import com.dicane.csvreader.screens.ScreenState
import kotlinx.coroutines.CoroutineScope

open class MockContactRepository(
    private val fromDbState: ScreenState = ScreenState.Error(),
    private val fromCsvState: ScreenState = ScreenState.Error()
) : ContactRepository {

        override suspend fun fetchFromDb(): List<Contact> {
            return when(fromDbState) {
                is ScreenState.Success -> MockUtil.contactList
                is ScreenState.Error -> throw RuntimeException("Some error")
                is ScreenState.Empty -> emptyList()
                is ScreenState.Loading -> emptyList()
            }
        }

        override suspend fun fetchFromCsv(context: Context, scope: CoroutineScope): List<Contact> {
            return when(fromCsvState) {
                is ScreenState.Success -> MockUtil.contactList
                is ScreenState.Error -> throw RuntimeException("Some error")
                is ScreenState.Empty -> emptyList()
                is ScreenState.Loading -> emptyList()
            }
        }

        override suspend fun update(contact: Contact) {}
        override suspend fun insertIntoDb(contact: Contact) {}
    }