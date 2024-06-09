package com.example.telegramsms

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.telegramsms.coordinators.dataCoordinator.DataCoordinator

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        DataCoordinator.shared.initialize(applicationContext, onLoad = {})

        val saveBtn = findViewById<Button>(R.id.save)
        saveBtn.setOnClickListener {
            val botTokenInput = findViewById<EditText>(R.id.bot_token)
            val chatIdInput = findViewById<EditText>(R.id.chat_id)
            val trustedPhoneNumberInput = findViewById<EditText>(R.id.trusted_phone_number)

            val botToken = botTokenInput.text
            val chatId = chatIdInput.text
            val trustedPhoneNumber = trustedPhoneNumberInput.text

            Log.d(
                "MainActivity",
                "BotToken: $botToken, ChatID: $chatId, TrustedPhoneNumber: $trustedPhoneNumber"
            )
        }
    }
}