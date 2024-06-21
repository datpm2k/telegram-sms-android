package com.example.telegramsms.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {
    val TELEGRAM_BOT_TOKEN = stringPreferencesKey("TELEGRAM_BOT_TOKEN")
    val TELEGRAM_CHAT_ID = stringPreferencesKey("TELEGRAM_CHAT_ID")
    val TELEGRAM_TRUSTED_PHONE_NUMBERS = stringPreferencesKey("TELEGRAM_TRUSTED_PHONE_NUMBERS")
}