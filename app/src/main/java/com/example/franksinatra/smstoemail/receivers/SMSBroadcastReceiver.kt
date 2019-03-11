package com.example.franksinatra.smstoemail.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import com.example.franksinatra.smstoemail.utils.FeedbackEmail
import java.lang.StringBuilder


class SMSBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val pduArray = intent?.getExtras()!!.get("pdus") as Array<Any>

        val messages = arrayOfNulls<SmsMessage>(pduArray.size)
        for (i in pduArray.indices) {
            messages[i] = SmsMessage.createFromPdu(pduArray[i] as ByteArray)
        }
        val b: Boolean
        val phoneNumber: String = messages[0]?.getDisplayOriginatingAddress() as String;
        val message = messages[0]?.getMessageBody() as String

        if (phoneNumber != null && !phoneNumber.equals("") && message != null && !message.equals("")) {
            val feedbackEmail: FeedbackEmail = FeedbackEmail()

            val context: String = StringBuilder()
                .append("phoneNumber: " + phoneNumber)
                .append("\n")
                .append("message: " + message)
                .toString()

            var params: Bundle = Bundle()

            params.putString("email", "rastinal@mail.ru")
            params.putString("subject", "SMS from " + phoneNumber)
            params.putString("context", context)

            feedbackEmail.params = params

            feedbackEmail.send()
        }
    }
}