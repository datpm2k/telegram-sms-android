package com.example.telegramsms.coordinators.dataCoordinator

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.telegramsms.models.constants.DebuggingIdentifiers

class DataCoordinator {
    companion object {
        const val IDENTIFIER = "[DataCoordinator]"

        fun shared(): DataCoordinator {
            return DataCoordinator()
        }
    }

    // MARK: Variables
    private var context: Context? = null

    // MARK: Data Store Variables
    val Context.dataStore by preferencesDataStore(name = "PreferenceDataStore")

    // Create a variable for each preference, along with a default value.
    // This is to guarantee that if it can't find it it resets to a value that you can control.
    var telegramBotTokenVariable: String = ""
    var telegramChatIdVariable: String = ""
    var telegramTrustedPhoneNumbersVariable: String = ""

    // API
    var apiRequestQueue: RequestQueue? = null

    // MARK: Lifecycle
    fun initialize(context: Context, onLoad: () -> Unit) {
        Log.i(
            IDENTIFIER,
            "${DebuggingIdentifiers.ACTION_OR_EVENT_IN_PROGRESS} initialize  ${DebuggingIdentifiers.ACTION_OR_EVENT_IN_PROGRESS}."
        )
        // Set Context
        this.context = context
        this.apiRequestQueue = Volley.newRequestQueue(context)
        // Callback
        onLoad()
    }
}