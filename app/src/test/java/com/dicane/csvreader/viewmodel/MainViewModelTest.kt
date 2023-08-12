package com.dicane.csvreader.viewmodel

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.dicane.csvreader.MockUtil
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.screens.ScreenState
import com.dicane.csvreader.usecase.FetchContactsUseCase
import com.dicane.csvreader.usecase.MockContactRepository
import com.dicane.csvreader.usecase.UpdateContactUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var contactRepository: MockContactRepository
    @Mock
    private lateinit var updateContactUseCase: UpdateContactUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when FetchContactsUseCase returns SUCCESS expect stateFlow as LIST OF CONTACTS `() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(this.testScheduler)
        val stateSuccess = ScreenState.Success(MockUtil.contactList)
        val mockFetchContactsUseCase = MockFetchContactsUseCase(stateSuccess)
        val viewModel = MainViewModel(mockFetchContactsUseCase, updateContactUseCase, testDispatcher)

        viewModel.fetchData(mockContext)

        viewModel.contactsState.test {
            val item = awaitItem()
            assertEquals(stateSuccess, item)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when FetchContactsUseCase returns ERROR expect stateFlow as ERROR STATE`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(this.testScheduler)
        val stateError = ScreenState.Error(Exception())
        val mockFetchContactsUseCase = MockFetchContactsUseCase(stateError)
        val viewModel = MainViewModel(mockFetchContactsUseCase, updateContactUseCase, testDispatcher)

        viewModel.fetchData(mockContext)

        viewModel.contactsState.test {
            val item = awaitItem()
            assertEquals(stateError, item)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when setLoadingState IS CALLED expect stateFlow to contain a LOADING STATE`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(this.testScheduler)
        val stateLoading = ScreenState.Loading
        val mockFetchContactsUseCase = MockFetchContactsUseCase(stateLoading)
        val viewModel = MainViewModel(mockFetchContactsUseCase, updateContactUseCase, testDispatcher)

        viewModel.setLoadingState()

        viewModel.contactsState.test {
            val item = awaitItem()
            assertEquals(stateLoading, item)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when updateContact IS CALLED expect updateContactUseCase TO BE CALLED`() {
        runTest {
            val testDispatcher = UnconfinedTestDispatcher(this.testScheduler)
            Dispatchers.setMain(testDispatcher)
            val stateError = ScreenState.Error(Exception())
            val mockFetchContactsUseCase = MockFetchContactsUseCase(stateError)
            val mockUpdateContactUseCase = MockUpdateContactUseCase()
            val viewModel =
                MainViewModel(mockFetchContactsUseCase, mockUpdateContactUseCase, testDispatcher)

            val result = viewModel.updateContact(
                1, null,
                TextFieldValue("James"),
                TextFieldValue("Butt"),
                TextFieldValue("Benton, John B Jr"),
                TextFieldValue("6649 N Blue Gum St"),
                TextFieldValue("New Orleans"),
                TextFieldValue("Orleans"),
                TextFieldValue("LA"),
                TextFieldValue("70116"),
                TextFieldValue("504-621-8927"),
                TextFieldValue("504-845-1427"),
                TextFieldValue("jbutt@gmail.com")
            )

            assertEquals(Unit, result)
        }
    }

    inner class MockUpdateContactUseCase : UpdateContactUseCase(contactRepository) {
        override suspend fun invoke(contact: Contact) = Unit
    }

    inner class MockFetchContactsUseCase(private val state: ScreenState) : FetchContactsUseCase(contactRepository) {
        override suspend fun invoke(context: Context, scope: CoroutineScope): ScreenState = state
    }
}