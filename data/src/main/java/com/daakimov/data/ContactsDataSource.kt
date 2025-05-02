package com.daakimov.data

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import com.daakimov.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContactsDataSource(context: Context) {

    val cursor: Cursor? = context.contentResolver.query(
        Contacts.CONTENT_URI,
        arrayOf(Contacts._ID, Contacts.DISPLAY_NAME),
        "${Contacts.HAS_PHONE_NUMBER} = '1'",
         null,
        "${Contacts.DISPLAY_NAME} asc"
    )


    suspend fun m(retry: Boolean = true): Flow<List<ContactModel>> = flow {

        val resList = mutableListOf<ContactModel>()

        cursor.use { c ->
            if(c == null) {
                if (retry) m(retry = false)
            } else {
                for (pos in 0 until c.count) {
                    c.moveToPosition(pos)
                    resList.add(
                        ContactModel(
                            id =  c.getInt(c.getColumnIndexOrThrow(Contacts._ID)),
                            name = c.getString(c.getColumnIndexOrThrow(Contacts.DISPLAY_NAME))
                        )
                    )
                }
            }
        }

        emit(resList.toList())

    }


}