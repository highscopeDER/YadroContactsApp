package com.daakimov.yadrocontactsapp.di

import android.content.Context
import com.daakimov.data.ContactsDataSource
import com.daakimov.data.ContactsRepositoryImpl
import com.daakimov.domain.repositories.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideContactsDataSource(@ApplicationContext context: Context): ContactsDataSource
        = ContactsDataSource(context)

    @Provides
    @Singleton
    fun provideContactsRepository(dataSource: ContactsDataSource): ContactsRepository
        = ContactsRepositoryImpl(dataSource)


}