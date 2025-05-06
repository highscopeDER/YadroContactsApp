package com.daakimov.yadrocontactsapp.di

import com.daakimov.domain.repositories.ContactsRepository
import com.daakimov.domain.usecases.GetContactsUseCase
import com.daakimov.domain.usecases.RequestDuplicatesDeletionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetContactsUseCase(contactsRepository: ContactsRepository): GetContactsUseCase = GetContactsUseCase(contactsRepository)

    @Provides
    fun provideRequestDuplicatesDeletionUseCase(contactsRepository: ContactsRepository) = RequestDuplicatesDeletionUseCase(contactsRepository)

}