package com.dicane.csvreader.repository

import android.content.Context
import android.content.res.Resources
import com.dicane.csvreader.MockUtil
import com.dicane.csvreader.database.ContactDao
import com.dicane.csvreader.datasource.CsvDatasource
import com.dicane.csvreader.model.Contact
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContactRepositoryTest {
    private lateinit var repository: ContactRepositoryImpl

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockContactDao: ContactDao

    @Mock
    private lateinit var mockCsvDatasource: CsvDatasource

    private val mockContacts = MockUtil.contactList

    private val mockContact = Contact(null, "James", "Butt", "Benton, John B Jr", "6649 N Blue Gum St", "New Orleans", "Orleans", "LA", "70116", "504-621-8927", "504-845-1427", "jbutt@gmail.com")

    @Before
    fun setup() {
        repository = ContactRepositoryImpl(mockContactDao, mockCsvDatasource)
    }

    @Test
    fun `when calling fetchFromDb FROM REPO expect getAllContacts FROM DAO to be called`() = runTest {
        Mockito.`when`(mockContactDao.getAllContacts()).thenReturn(mockContacts)

        val result = repository.fetchFromDb()

        assertEquals(mockContacts, result)
    }

    @Test
    fun `when calling update FROM REPO expect update FROM DAO to be called`() = runTest {
        repository.update(mockContact)

        Mockito.verify(mockContactDao).update(mockContact)
    }

    @Test
    fun `when calling insertIntoDb FROM REPO expect insert FROM DAO to be called`() = runTest {
        repository.insertIntoDb(mockContact)

        Mockito.verify(mockContactDao).insert(mockContact)
    }

    @Test
    fun `when calling fetchFromCsv FROM REPO expect fetchFromCsv FROM DAO to be called`() = runTest {
        val mockResources = Mockito.mock(Resources::class.java)
        val inputStream = this.javaClass.classLoader.getResourceAsStream("sample_contacts.csv")
        val scope = CoroutineScope(testScheduler)

        Mockito.`when`(mockContext.resources).thenReturn(mockResources)
        Mockito.`when`(mockResources.openRawResource(Mockito.anyInt())).thenReturn(inputStream)

        repository.fetchFromCsv(mockContext, scope)

        Mockito.verify(mockCsvDatasource).fetchFromCsv(mockContext, scope)
    }
}