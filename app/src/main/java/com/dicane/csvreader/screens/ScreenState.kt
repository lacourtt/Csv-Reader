package com.dicane.csvreader.screens

import com.dicane.csvreader.model.Contact
import java.lang.Exception

sealed class ScreenState {
    data class Success(var contacts : List<Contact> = emptyList()): ScreenState()
    data class Error(val exception: Exception? = null): ScreenState()
    object Loading: ScreenState()
    object Empty: ScreenState()
}