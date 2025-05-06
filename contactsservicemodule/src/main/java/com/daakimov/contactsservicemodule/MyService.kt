package com.daakimov.contactsservicemodule

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return object : AidlInterface.Stub() {
            override fun deleteDuplicates(): String {
                return "aidl test"
            }

        }
    }
}