package com.example.telegramsms.coordinators.dataCoordinator

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.HttpHeaderParser
import com.example.telegramsms.coordinators.dataCoordinator.DataCoordinator.Companion.IDENTIFIER
import com.example.telegramsms.models.api.ErrorResponse
import com.example.telegramsms.models.api.ForwardSMSResponse
import com.example.telegramsms.models.constants.DebuggingIdentifiers
import com.example.telegramsms.utils.data.GsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

fun DataCoordinator.callAPI(payload: JSONObject) {
    callAPI(
        payload,
        onSuccess = {},
        onError = {}
    )
}

fun DataCoordinator.callAPI(
    payload: JSONObject,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    val apiRequestQueue = this.apiRequestQueue ?: return

    // Create the headers
    val headers: MutableMap<String, String> = HashMap()
    headers["x-api-key"] = "Aqswde123@@"
    headers["content-type"] = "application/json"

    val request = GsonRequest(
        "https://d5e7-123-21-117-25.ngrok-free.app/api/v1/sms/forward",
        clazz = ForwardSMSResponse::class.java,
        method = Request.Method.POST,
        headers = headers,
        jsonPayload = payload,
        listener = {
            Log.i(
                IDENTIFIER,
                "${DebuggingIdentifiers.ACTION_OR_EVENT_SUCCEEDED} request : $it.",
            )
            onSuccess()
        },
        errorListener = {
            val response = it.networkResponse
            try {
                val errorJson = String(
                    response.data,
                    Charset.forName(HttpHeaderParser.parseCharset(response.headers))
                )
                val errorObj = Gson().fromJson(errorJson, ErrorResponse::class.java)
                Log.i(
                    IDENTIFIER,
                    "${DebuggingIdentifiers.ACTION_OR_EVENT_FAILED} request : ${errorObj.error}",
                )
                onError()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }
        }
    )

    // Make the request
    apiRequestQueue.add(request)
}