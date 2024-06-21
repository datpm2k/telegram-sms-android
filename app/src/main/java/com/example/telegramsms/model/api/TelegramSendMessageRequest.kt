package com.example.telegramsms.model.api

import com.google.gson.annotations.SerializedName

data class TelegramSendMessageRequest(
    @SerializedName("chat_id")
    val chatId: String,
    val text: String,
)
