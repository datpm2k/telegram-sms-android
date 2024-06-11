package com.example.telegramsms.models.api

class BaseResponse<T>(
    val code: String,
    val message: String,
    val timestamp: String,
    val data: T,
)