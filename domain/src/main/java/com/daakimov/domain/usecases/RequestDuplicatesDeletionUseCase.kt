package com.daakimov.domain.usecases

import com.daakimov.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow

class RequestDuplicatesDeletionUseCase(private val contactsRepository: ContactsRepository) {

    suspend fun execute(): Flow<String> {
        return contactsRepository.deleteDuplicates()
    }

}