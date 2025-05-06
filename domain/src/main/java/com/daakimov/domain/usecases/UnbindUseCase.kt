package com.daakimov.domain.usecases

import com.daakimov.domain.repositories.ContactsRepository

class UnbindUseCase(private val contactsRepository: ContactsRepository) {

    suspend fun execute(){
        contactsRepository.unbind()
    }

}