package com.dicane.csvreader.datasource

import android.content.Context
import android.content.res.Resources
import com.dicane.csvreader.MockUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class CsvDatasourceTest {
    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockResources: Resources

    private lateinit var csvDatasource: CsvDatasource

    private val contactList = MockUtil.contactList

    @Before
    fun setUp() {
        csvDatasource = CsvDatasource()
    }
    @Test
    fun `when fetch from csv file expect list of contacts`() = runTest {

        // arrange
        val inputStream = this.javaClass.classLoader.getResourceAsStream("sample_contacts.csv")

        `when`(mockContext.resources).thenReturn(mockResources)
        `when`(mockResources.openRawResource(anyInt())).thenReturn(inputStream)

        // act
        val result = CsvDatasource().fetchFromCsv(mockContext, CoroutineScope(testScheduler))

        // assert
        assertEquals(contactList, result.subList(0, 3))
    }

}