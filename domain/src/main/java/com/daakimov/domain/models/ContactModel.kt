package com.daakimov.domain.models

data class ContactModel (
    val id: Int,
    val name: String,
    val phone: String,
    val photoUri: String? = null
)