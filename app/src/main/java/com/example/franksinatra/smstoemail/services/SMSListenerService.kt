package com.example.franksinatra.smstoemail.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.widget.Toast
import com.example.franksinatra.smstoemail.MainActivity
import com.example.franksinatra.smstoemail.receivers.SMSBroadcastReceiver

class SMSListenerService : Service() {

    lateinit var intentReceiver: SMSBroadcastReceiver
    lateinit var mainActivity: MainActivity
    var intentFilter: IntentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")


    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    companion object {
        val SERVICE_ACTION = "com.example.franksinatra.smstoemail/com.example.franksinatra.smstoemail.services.SMSListenerService"
    }

    override fun onCreate() {
        super.onCreate()
        mainActivity = MainActivity.instance
        intentReceiver = SMSBroadcastReceiver()

        Toast.makeText(this, "Служба создана", Toast.LENGTH_LONG).show();
        mainActivity.registerReceiver(intentReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }
}
