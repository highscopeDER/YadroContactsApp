package com.daakimov.yadrocontactsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daakimov.domain.models.ContactModel
import com.daakimov.domain.models.ContactsRcList
import com.daakimov.domain.usecases.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val contactsRcAdapter = ContactsRcAdapter()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getContactsUseCase.execute().collectLatest {
                contactsRcAdapter.setData(it.list)
                _uiState.value = UiState.ShowContacts(it)
            }
        }
    }

    fun getRcAdapter() = contactsRcAdapter

    fun call(phoneNumber: String) {

    }

    sealed class UiState() {
        data object Empty: UiState()
        data object Loading: UiState()
        class ShowContacts(val list: ContactsRcList): UiState()
    }

}