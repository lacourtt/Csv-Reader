package com.dicane.csvreader.usecase;

import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.repository.ContactRepository

open class UpdateContactUseCase(
    private val contactRepository: ContactRepository
) {
    open suspend operator fun invoke(contact: Contact) {
        contactRepository.update(contact)
    }
}
