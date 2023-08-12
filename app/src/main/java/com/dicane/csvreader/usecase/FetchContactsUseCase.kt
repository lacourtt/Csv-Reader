package com.dicane.csvreader.usecase

import android.content.Context
import com.dicane.csvreader.repository.ContactRepository
import com.dicane.csvreader.screens.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

open class FetchContactsUseCase(
    private val contactRepository: ContactRepository
) {
    open suspend operator fun invoke(context: Context, scope: CoroutineScope): ScreenState = scope.async {
        var state = try {
            val contacts = contactRepository.fetchFromDb()
            ScreenState.Success(contacts)
        } catch (e: Exception) {
            ScreenState.Error(e)
        }

        if (state is ScreenState.Error) {
            state = try {
                val contacts = contactRepository.fetchFromCsv(context, scope)
                ScreenState.Success(contacts)
            } catch (e: Exception) {
                ScreenState.Error(e)
            }
        } else if ((state as ScreenState.Success).contacts.isEmpty()) {
            state = try {
                val contacts = contactRepository.fetchFromCsv(context, scope)
                ScreenState.Success(contacts)
            } catch (e: Exception) {
                ScreenState.Error(e)
            }
        }

        (state as? ScreenState.Success)?.contacts?.map {
            contactRepository.insertIntoDb(it)
        }
        state
    }.await()
}
