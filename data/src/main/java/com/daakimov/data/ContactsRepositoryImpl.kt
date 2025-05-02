package com.daakimov.data

import android.content.Context
import com.daakimov.domain.models.ContactModel
import com.daakimov.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow

class ContactsRepositoryImpl(private val dataSource: ContactsDataSource) : ContactsRepository {

    override suspend fun retrieveContacts(): Flow<List<ContactModel>> = dataSource.m()


}