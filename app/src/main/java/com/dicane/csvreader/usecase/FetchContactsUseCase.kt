package com.dicane.csvreader.usecase

import android.content.Context
import com.dicane.csvreader.repository.ContactRepository
import com.dicane.csvreader.screens.ScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope

class FetchContactsUseCase(
    private val contactRepository: ContactRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(context: Context): ScreenState = coroutineScope {
        var state = try {
            val contacts = contactRepository.fetchFromDb()
            ScreenState.Success(contacts)
        } catch (e: Exception) {
            ScreenState.Error(e)
        }

        if (state is ScreenState.Success) {
            if (state.contacts.isEmpty()) {
                state = try {
                    val contacts = contactRepository.fetchFromCsv(context, ioDispatcher)
                    ScreenState.Success(contacts)
                } catch (e: Exception) {
                    ScreenState.Error(e)
                }
            }

            (state as ScreenState.Success).contacts.map {
                contactRepository.insertIntoDb(it)
            }
        }

        state
    }
}