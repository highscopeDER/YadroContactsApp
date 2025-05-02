package com.daakimov.yadrocontactsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daakimov.domain.usecases.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {

    init {
        println("viewmodel init")
        viewModelScope.launch {
            println("executing use case")
            getContactsUseCase.execute().collectLatest {
                println(it)
            }
            println("execution finished")
        }
    }

}