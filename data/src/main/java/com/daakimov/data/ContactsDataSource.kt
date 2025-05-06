package com.daakimov.data

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds
import android.provider.ContactsContract.Contacts
import android.provider.ContactsContract.RawContacts
import androidx.core.database.getStringOrNull
import com.daakimov.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContactsDataSource(private val context: Context) {

    private val TAG = "CONTACTS_DATA_SOURCE_LOG"

    private val selection = "(${CommonDataKinds.Phone.CONTACT_ID} in " +
            "(select ${Contacts._ID} from Contacts" +
            " where ${Contacts.HAS_PHONE_NUMBER} = '1'))"

    private fun fetchContactsWithPhoneNumber(): List<ContactModel> = context.contentResolver.query(
        CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            CommonDataKinds.Phone.CONTACT_ID,
            CommonDataKinds.Phone.DISPLAY_NAME,
            CommonDataKinds.Phone.NUMBER,
            CommonDataKinds.Phone.PHOTO_URI,
            RawContacts.ACCOUNT_TYPE,
        ),
        selection,
        null,
        "${CommonDataKinds.Phone.DISPLAY_NAME} asc"
        ).use {
            val res = mutableListOf<ContactModel>()

            if (it != null)
                for (pos in 0 until it.count) {
                    it.moveToPosition(pos)
                    val accType: String? = it.getStringOrNull(it.getColumnIndexOrThrow(RawContacts.ACCOUNT_TYPE))
                    if (accType?.contains(".phone") != false) res.add(
                        ContactModel(
                            id =  it.getInt(it.getColumnIndexOrThrow(CommonDataKinds.Phone.CONTACT_ID)),
                            name = it.getString(it.getColumnIndexOrThrow(CommonDataKinds.Phone.DISPLAY_NAME)),
                            phone = it.getString(it.getColumnIndexOrThrow(CommonDataKinds.Phone.NUMBER)),
                            photoUri = it.getString(it.getColumnIndexOrThrow(CommonDataKinds.Phone.PHOTO_URI))
                        )
                    )
                }

        res.toList()
    }

    fun getContacts(): Flow<List<ContactModel>> = flow { emit(fetchContactsWithPhoneNumber()) }

    fun wtf(){
        val intent = Intent("com.daakimov.contactsservicemodule.SERVICE_ACCESS")
        val services = context.packageManager.queryIntentServices(intent, 0)
        if (services.isEmpty()) {
            throw IllegalStateException("Приложение-сервер не установлено")
        }

        val res = Intent(intent).apply {
            val resolveInfo = services[0]
            val packageName = resolveInfo.serviceInfo.packageName
            val className = resolveInfo.serviceInfo.name
            component = ComponentName(packageName, className)
        }

        context.bindService(res, ContactsServiceConnection, BIND_AUTO_CREATE)

    }

    fun wtff(){
        context.unbindService(ContactsServiceConnection)
    }



}