package com.example.telegramsms.client

import android.util.Log
import com.example.telegramsms.model.api.TelegramSendMessageRequest
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


val APPLICATION_JSON: MediaType = "application/json".toMediaType()


class TelegramClient {
    private val gson: Gson = Gson().newBuilder().create()

    companion object {
        const val IDENTIFIER = "[TelegramClient]"
    }

    fun sendMessage(
        botToken: String,
        chatId: String,
        text: String
    ) {
        try {
            Thread {
                val request = TelegramSendMessageRequest(chatId, text)
                val requestJson = gson.toJson(request)

                val url = String.format("https://api.telegram.org/bot$botToken/sendMessage")

                val okHttpClient = OkHttpClient()
                val requestBody = requestJson.toRequestBody(APPLICATION_JSON)
                val httpRequest = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                okHttpClient.newCall(httpRequest).execute()
            }.start()
        } catch (e: Exception) {
            Log.e(IDENTIFIER, e.message?: "Call API error")
        }
    }
}