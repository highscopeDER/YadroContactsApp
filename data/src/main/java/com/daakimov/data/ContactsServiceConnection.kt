package com.daakimov.data

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.daakimov.contactsservicemodule.AidlInterface


object ContactsServiceConnection : ServiceConnection {

    private var aidlInterface: AidlInterface? = null

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        aidlInterface = AidlInterface.Stub.asInterface(p1)
        val res = aidlInterface?.deleteDuplicates()
        Log.d("AIDL INTERFACE TEST", "res: $res")
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        TODO("Not yet implemented")
    }
}