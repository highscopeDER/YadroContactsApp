package com.daakimov.domain.usecases

import com.daakimov.domain.models.ContactModel
import com.daakimov.domain.models.ContactsRcList
import com.daakimov.domain.models.RcListCell
import com.daakimov.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.transform

class GetContactsUseCase(private val contactsRepository: ContactsRepository) {

    suspend fun execute(): Flow<ContactsRcList> = contactsRepository.requestContacts().transform {

        emit(
            ContactsRcList(
                it
                    .map { model -> model.name.first() }
                    .distinct()
                    .flatMap { head: Char ->
                        mutableListOf<RcListCell>(RcListCell.Header(head.toString())).apply {
                            addAll(
                                it
                                .filter { model -> model.name.first() == head}
                                .map { model -> RcListCell.Contact(data = model) }
                            )

                        }
                    }
            )
        )

    }

}