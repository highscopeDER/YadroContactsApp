package com.daakimov.contactsservicemodule

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract.CommonDataKinds
import android.provider.ContactsContract.Contacts
import android.provider.ContactsContract.RawContacts
import android.util.Log
import androidx.core.database.getStringOrNull

class MyService : Service() {


    private val tag = "SERVICE TEST:"

    private val selection = "(${CommonDataKinds.Phone.CONTACT_ID} in " +
            "(select ${Contacts._ID} from Contacts" +
            " where ${Contacts.HAS_PHONE_NUMBER} = '1'))"

    private fun fetchDuplicates(): List<String> = contentResolver.query(
        CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            CommonDataKinds.Phone.CONTACT_ID,
            CommonDataKinds.Phone.DISPLAY_NAME,
            CommonDataKinds.Phone.NUMBER,
            CommonDataKinds.Phone.RAW_CONTACT_ID,
            RawContacts.ACCOUNT_TYPE,
        ),
        selection,
        null,
        "${CommonDataKinds.Phone.DISPLAY_NAME} asc"
    ).use {
        val res = mutableListOf<Contact>()

        if (it != null)
            for (pos in 0 until it.count) {
                it.moveToPosition(pos)
                val accType: String? = it.getStringOrNull(it.getColumnIndexOrThrow(RawContacts.ACCOUNT_TYPE))
                if (accType?.contains(".phone") != false) res.add(
                    Contact(
                        id =  it.getInt(it.getColumnIndexOrThrow(CommonDataKinds.Phone.RAW_CONTACT_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(CommonDataKinds.Phone.DISPLAY_NAME)),
                        phone = it.getString(it.getColumnIndexOrThrow(CommonDataKinds.Phone.NUMBER)),
                    )
                )
            }

        Log.d(tag, res.toString())

        val www: List<Pair<String, String>> = res.map { it.name to it.phone }

        Log.d(tag, "www: $www")

        val intr = www.distinct().filter { d ->
            www.count {d == it} > 1
        }.toSet()

        Log.d(tag, "intersection: $intr")
        val m = intr.mapNotNull {
            res.find { contact -> contact.name == it.first || contact.phone == it.second }?.id?.toString()
        }

        Log.d(tag, "id's: $m")

        m.toList()
    }

    override fun onBind(intent: Intent): IBinder {
        return object : AidlInterface.Stub() {
            override fun deleteDuplicates(): String {
                val arr = fetchDuplicates()

                return if (arr.isEmpty()) {
                    "there are no contacts to delete!"
                } else {
                    contentResolver.delete(
                        RawContacts.CONTENT_URI,
                        "${RawContacts._ID} in (${arr.toString().trim('[', ']')})",
                        null)
                    "duplicated contacts deleted!"
                }



            }

        }
    }
}