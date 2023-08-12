package com.dicane.csvreader.usecase

import com.dicane.csvreader.database.ContactDao
import com.dicane.csvreader.datasource.CsvDatasource
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.repository.ContactRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify

class UpdateContactUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when calling UpdateContactUseCase repo update fun is called`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        val contact = Contact(null, "James", "Butt", "Benton, John B Jr", "6649 N Blue Gum St", "New Orleans", "Orleans", "LA", "70116", "504-621-8927", "504-845-1427", "jbutt@gmail.com")
        val mockContactRepository = mock(ContactRepositoryImpl::class.java)

        UpdateContactUseCase(mockContactRepository).invoke(contact)

        verify(mockContactRepository).update(contact)
    }
}