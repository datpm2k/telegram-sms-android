package com.example.telegramsms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.example.telegramsms.client.TelegramClient
import com.example.telegramsms.data.local.PreferenceDataStoreConstants
import com.example.telegramsms.data.local.PreferenceDataStoreHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


class SmsBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val IDENTIFIER = "[SmsBroadcastReceiver]"
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(IDENTIFIER, "onReceive")

        val dataStore = PreferenceDataStoreHelper(context!!)

        var botToken: String
        var chatId: String
        var trustedPhoneNumbersStr: String
        runBlocking {
            botToken = dataStore.getFirstPreference(
                PreferenceDataStoreConstants.TELEGRAM_BOT_TOKEN, ""
            )
            chatId = dataStore.getFirstPreference(
                PreferenceDataStoreConstants.TELEGRAM_CHAT_ID, ""
            )
            trustedPhoneNumbersStr = dataStore.getFirstPreference(
                PreferenceDataStoreConstants.TELEGRAM_TRUSTED_PHONE_NUMBERS, ""
            )
        }

        if (botToken === "" || chatId == "") return

        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)

                        val from = smsMessage.displayOriginatingAddress
                        val content = smsMessage.displayMessageBody

                        Log.i(IDENTIFIER, "From: $from, Content: $content")

                        if (trustedPhoneNumbersStr.trim() !== ""
                            && !trustedPhoneNumbersStr.split(",").contains(from)
                        ) {
                            return
                        }

                        val text = """
                            From: $from
                            Content: $content
                        """.trimIndent()

                        val telegramClient = TelegramClient()
                        telegramClient.sendMessage(botToken, chatId, text)
                    }
                }
            }
        }
    }
}