package com.example.telegramsms.coordinators.dataCoordinator

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.telegramsms.models.constants.DebuggingIdentifiers

class DataCoordinator {
    companion object {
        val shared = DataCoordinator()
        const val IDENTIFIER = "[DataCoordinator]"
    }

    // MARK: Variables
    private var context: Context? = null

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