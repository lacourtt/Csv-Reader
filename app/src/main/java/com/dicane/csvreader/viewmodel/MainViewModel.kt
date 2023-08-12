package com.dicane.csvreader.viewmodel

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.screens.ScreenState
import com.dicane.csvreader.usecase.FetchContactsUseCase
import com.dicane.csvreader.usecase.UpdateContactUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _contactsState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val contactsState: StateFlow<ScreenState> = _contactsState

    fun fetchData(context: Context) {
        viewModelScope.launch(ioDispatcher) {
            val item = fetchContactsUseCase(context, this)
            _contactsState.value = item
        }
    }

    fun getContact(index: Int) =
        (contactsState.value as ScreenState.Success).contacts[index]

    fun updateContact(
        index: Int,
        id: Int?,
        firstName: TextFieldValue,
        lastName: TextFieldValue,
        companyName: TextFieldValue,
        email: TextFieldValue,
        phone: TextFieldValue,
        phone1: TextFieldValue,
        address: TextFieldValue,
        city: TextFieldValue,
        county: TextFieldValue,
        state: TextFieldValue,
        zip: TextFieldValue
    ) {
        val updatedContact = Contact(
            id = id,
            firstName = firstName.text,
            lastName = lastName.text,
            companyName = companyName.text,
            email = email.text,
            phone = phone.text,
            phone1 = phone1.text,
            address = address.text,
            city = city.text,
            county = county.text,
            state = state.text,
            zip = zip.text
        )

        viewModelScope.launch {
            updateContactUseCase(updatedContact)
            if (_contactsState.value is ScreenState.Success) {
                (_contactsState.value as ScreenState.Success).contacts = (_contactsState.value as ScreenState.Success).contacts.toMutableList().apply {
                    set(index, updatedContact)
                }
            }
        }
    }

fun setLoadingState() {
        _contactsState.value = ScreenState.Loading
    }

}

