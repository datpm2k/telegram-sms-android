package com.example.telegramsms

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.telegramsms.data.local.PreferenceDataStoreHelper
import com.example.telegramsms.data.local.PreferenceDataStoreConstants
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    companion object {
        const val IDENTIFIER = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val editTextBotToken = findViewById<EditText>(R.id.bot_token)
        val editTextChatId = findViewById<EditText>(R.id.chat_id)
        val editTextTrustedPhoneNumbers = findViewById<EditText>(R.id.trusted_phone_number)

        val dataStore = PreferenceDataStoreHelper(applicationContext)
        var botToken: String
        var chatId: String
        var trustedPhoneNumbers: String
        runBlocking {
            botToken = dataStore.getFirstPreference(
                PreferenceDataStoreConstants.TELEGRAM_BOT_TOKEN, ""
            )
            chatId = dataStore.getFirstPreference(
                PreferenceDataStoreConstants.TELEGRAM_CHAT_ID, ""
            )
            trustedPhoneNumbers = dataStore.getFirstPreference(
                PreferenceDataStoreConstants.TELEGRAM_TRUSTED_PHONE_NUMBERS, "5298"
            )
        }

        editTextBotToken.setText(botToken)
        editTextChatId.setText(chatId)
        editTextTrustedPhoneNumbers.setText(trustedPhoneNumbers)

        val saveBtn = findViewById<Button>(R.id.save)
        saveBtn.setOnClickListener {
            botToken = editTextBotToken.text.toString()
            chatId = editTextChatId.text.toString()
            trustedPhoneNumbers = editTextTrustedPhoneNumbers.text.toString()

            Log.d(
                IDENTIFIER,
                "Updated: BotToken: $botToken, ChatID: $chatId, TrustedPhoneNumber: $trustedPhoneNumbers"
            )

            // Put data to data store
            lifecycleScope.launch {
                dataStore.putPreference(
                    PreferenceDataStoreConstants.TELEGRAM_BOT_TOKEN,
                    botToken
                )
                dataStore.putPreference(
                    PreferenceDataStoreConstants.TELEGRAM_CHAT_ID,
                    chatId
                )
                dataStore.putPreference(
                    PreferenceDataStoreConstants.TELEGRAM_TRUSTED_PHONE_NUMBERS,
                    trustedPhoneNumbers
                )
            }
        }
    }
}