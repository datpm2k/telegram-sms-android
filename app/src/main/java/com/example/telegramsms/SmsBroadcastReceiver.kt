package com.example.telegramsms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import com.example.telegramsms.coordinators.dataCoordinator.DataCoordinator
import com.example.telegramsms.coordinators.dataCoordinator.callAPI
import org.json.JSONObject


class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("[SmsBroadcastReceiver]", "onReceive")

        var botToken: String = ""
        var chatId: String = ""
        var trustedPhoneNumbersStr: String = "5298"

        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)

                        val sender = smsMessage.displayOriginatingAddress
                        val message = smsMessage.displayMessageBody

                        Log.i("[SmsBroadcastReceiver]", "Sender: $sender, Message: $message")

                        if (trustedPhoneNumbersStr.trim() !== ""
                            && !trustedPhoneNumbersStr.split(",").contains(sender)
                        ) {
                            return
                        }

                        val apiRequest = JSONObject()
                        apiRequest.put("sender", sender)
                        apiRequest.put("message", message)
                        DataCoordinator.shared.callAPI(apiRequest)

                        Toast.makeText(
                            context,
                            "Sender: $sender\nMessage: $message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}