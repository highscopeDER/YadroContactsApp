package com.daakimov.data

import com.daakimov.domain.models.ContactModel
import com.daakimov.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContactsRepositoryImpl(private val dataSource: ContactsDataSource) : ContactsRepository {

    override suspend fun requestContacts(): Flow<List<ContactModel>> = dataSource.getContacts()

    override suspend fun deleteDuplicates(): Flow<String> {
        dataSource.wtf()
        return flow { emit("service start") }
    }

}