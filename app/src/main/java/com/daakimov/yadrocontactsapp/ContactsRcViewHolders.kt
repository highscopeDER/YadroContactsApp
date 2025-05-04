package com.daakimov.yadrocontactsapp

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.daakimov.domain.models.RcListCell
import com.daakimov.yadrocontactsapp.databinding.ContactsRcItemContactBinding
import com.daakimov.yadrocontactsapp.databinding.ContactsRcItemHeaderBinding

sealed class ContactsRcViewHolders(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(model: RcListCell) {}

    class Contact(private val binding: ContactsRcItemContactBinding) : ContactsRcViewHolders(binding) {

        override fun bind(model: RcListCell) {
            model as RcListCell.Contact
            binding.nameView.text = model.data.name
            binding.phoneView.text = model.data.phone
        }

    }

    class Header(private val binding: ContactsRcItemHeaderBinding) : ContactsRcViewHolders(binding) {

        override fun bind(model: RcListCell) {
            model as RcListCell.Header
            binding.headerTextView.text = model.text
        }

    }



}