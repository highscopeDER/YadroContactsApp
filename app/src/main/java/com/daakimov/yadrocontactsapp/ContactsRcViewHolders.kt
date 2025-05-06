package com.daakimov.yadrocontactsapp

import android.content.res.AssetFileDescriptor
import android.net.Uri
import androidx.appcompat.content.res.AppCompatResources
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

            model.data.photoUri?.let {
                binding.avatarView.setImageURI(Uri.parse(it))
            } ?: binding.avatarView.setImageDrawable(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.ic_launcher_foreground
                )
            )
        }

        fun setOnClickListener(function: () -> Unit?) {
            binding.root.setOnClickListener { function.invoke() }
        }

    }

    class Header(private val binding: ContactsRcItemHeaderBinding) : ContactsRcViewHolders(binding) {

        override fun bind(model: RcListCell) {
            model as RcListCell.Header
            binding.headerTextView.text = model.text
        }

    }



}