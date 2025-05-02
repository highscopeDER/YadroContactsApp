package com.daakimov.domain.repositories

import com.daakimov.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    suspend fun retrieveContacts(): Flow<List<ContactModel>>

}