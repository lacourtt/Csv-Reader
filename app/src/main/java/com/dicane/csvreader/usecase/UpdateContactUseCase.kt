package com.dicane.csvreader.usecase;

import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.repository.ContactRepository

class UpdateContactUseCase(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        contactRepository.update(contact)
    }
}
