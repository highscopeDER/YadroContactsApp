package com.daakimov.yadrocontactsapp

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daakimov.domain.usecases.GetContactsUseCase
import com.daakimov.domain.usecases.RequestDuplicatesDeletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val requestDuplicatesDeletionUseCase: RequestDuplicatesDeletionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _makePhoneCall = MutableSharedFlow<Intent>()
    val makePhoneCall = _makePhoneCall.asSharedFlow()

    private val _makeToast = MutableSharedFlow<String>()
    val makeToast = _makeToast.asSharedFlow()

    private val contactsRcAdapter = ContactsRcAdapter()

    init {
        contactsRcAdapter.setContactOnClickListener { contact ->
            viewModelScope.launch {
                _makePhoneCall.emit(
                    Intent(
                        Intent.ACTION_CALL,
                        Uri.parse("tel:${contact.data.phone}")
                    )
                )
            }
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getContactsUseCase.execute().collectLatest {
                contactsRcAdapter.setData(it.list)
                _uiState.value = UiState.ShowContacts
            }
        }
    }

    fun deleteDuplicates(){
        viewModelScope.launch {
            requestDuplicatesDeletionUseCase.execute().collectLatest {
                _makeToast.emit(it)
            }
        }
    }

    fun getRcAdapter() = contactsRcAdapter

    sealed class UiState() {
        data object Empty: UiState()
        data object Loading: UiState()
        data object ShowContacts : UiState()
    }

}