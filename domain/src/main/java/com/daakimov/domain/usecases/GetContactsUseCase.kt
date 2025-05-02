package com.daakimov.domain.usecases

import com.daakimov.domain.models.ContactModel
import com.daakimov.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow

class GetContactsUseCase(private val contactsRepository: ContactsRepository) {

    suspend fun execute(): Flow<List<ContactModel>> = contactsRepository.retrieveContacts()

}