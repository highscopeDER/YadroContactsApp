package com.daakimov.domain.models

sealed class RcListCell {

    class Header(val text: String) : RcListCell()
    class Contact(val data: ContactModel): RcListCell()

}