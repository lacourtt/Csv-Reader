package com.dicane.csvreader.usecase

import android.content.Context
import com.dicane.csvreader.database.ContactDao
import com.dicane.csvreader.datasource.CsvDatasource
import com.dicane.csvreader.repository.ContactRepositoryImpl
import com.dicane.csvreader.screens.ScreenState
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FetchContactsUseCaseTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockContactDao: ContactDao

    @Mock
    private lateinit var mockCsvDatasource: CsvDatasource

    private lateinit var contactRepository: ContactRepositoryImpl

    private lateinit var exception: RuntimeException


    @Before
    fun setup() {
        contactRepository = ContactRepositoryImpl(mockContactDao, mockCsvDatasource)
        exception = RuntimeException("Some error")
    }

    @Test
    fun `when INVOKING USE CASE fetch from DB returns SUCCESSFULLY expect SUCCESS`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        val testScope = CoroutineScope(testDispatcher)
        val mockContactRepository = MockContactRepository(
            fromDbState = ScreenState.Success(),
            fromCsvState = ScreenState.Success()
        )

        val result = FetchContactsUseCase(mockContactRepository)
            .invoke(mockContext, testScope)

        assertTrue(result is ScreenState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when INVOKING USE CASE fetch from DB returns EXCEPTION and CSV returns SUCCESSFULLY expect SUCCESS`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        val testScope = CoroutineScope(testDispatcher)
        val mockContactRepository = MockContactRepository(
            fromDbState = ScreenState.Error(),
            fromCsvState = ScreenState.Success()
        )

        val result = FetchContactsUseCase(mockContactRepository)
            .invoke(mockContext, testScope)

        assertTrue(result is ScreenState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when INVOKING USE CASE fetch from DB returns EMPTY and CSV returns SUCCESSFULLY expect SUCCESS`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        val testScope = CoroutineScope(testDispatcher)
        val mockContactRepository = MockContactRepository(
            fromDbState = ScreenState.Empty,
            fromCsvState = ScreenState.Success()
        )

        val result = FetchContactsUseCase(mockContactRepository)
            .invoke(mockContext, testScope)

        assertTrue(result is ScreenState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when INVOKING USE CASE fetch from DB returns EMPTY and CSV returns EXCEPTION expect ERROR`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        val testScope = CoroutineScope(testDispatcher)
        val mockContactRepository = MockContactRepository(
            fromDbState = ScreenState.Empty,
            fromCsvState = ScreenState.Error()
        )

        val result = FetchContactsUseCase(mockContactRepository)
            .invoke(mockContext, testScope)

        assertTrue(result is ScreenState.Error)
    }

    @Test
    fun `when INVOKING USE CASE fetch from DB and CSV return EXCEPTION expect ERROR`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        val testScope = CoroutineScope(testDispatcher)
        val mockContactRepository = MockContactRepository(
            fromDbState = ScreenState.Error(),
            fromCsvState = ScreenState.Error()
        )

        val result = FetchContactsUseCase(mockContactRepository)
            .invoke(mockContext, testScope)

        assertTrue(result is ScreenState.Error)
    }

}