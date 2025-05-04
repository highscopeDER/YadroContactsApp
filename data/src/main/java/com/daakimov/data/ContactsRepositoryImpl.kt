package com.daakimov.data

import com.daakimov.domain.models.ContactModel
import com.daakimov.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow

class ContactsRepositoryImpl(private val dataSource: ContactsDataSource) : ContactsRepository {

    override suspend fun requestContacts(): Flow<List<ContactModel>> = dataSource.getContacts()


}