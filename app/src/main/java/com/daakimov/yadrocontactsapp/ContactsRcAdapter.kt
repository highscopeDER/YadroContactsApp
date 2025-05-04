package com.daakimov.yadrocontactsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daakimov.domain.models.RcListCell
import com.daakimov.yadrocontactsapp.databinding.ContactsRcItemContactBinding
import com.daakimov.yadrocontactsapp.databinding.ContactsRcItemHeaderBinding


class ContactsRcAdapter : RecyclerView.Adapter<ContactsRcViewHolders>() {

    private val data: MutableList<RcListCell> = mutableListOf()

    fun setData(l: List<RcListCell>) {
        data.clear()
        data.addAll(l)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = when(data[position]) {
            is RcListCell.Header -> R.layout.contacts_rc_item_header
            is RcListCell.Contact -> R.layout.contacts_rc_item_contact
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsRcViewHolders = when(viewType) {
        R.layout.contacts_rc_item_header -> ContactsRcViewHolders.Header(
            ContactsRcItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        R.layout.contacts_rc_item_contact -> ContactsRcViewHolders.Contact(
            ContactsRcItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else -> throw Exception("error in ContactsRcAdapter OnCreateViewHolder")
    }


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ContactsRcViewHolders, position: Int) {
        holder.bind(data[position])
    }

}