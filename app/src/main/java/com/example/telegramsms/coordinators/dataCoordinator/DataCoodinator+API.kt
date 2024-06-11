package com.example.telegramsms.coordinators.dataCoordinator

import android.util.Log
import com.android.volley.Request
import com.example.telegramsms.coordinators.dataCoordinator.DataCoordinator.Companion.IDENTIFIER
import com.example.telegramsms.models.api.BaseResponse
import com.example.telegramsms.models.constants.DebuggingIdentifiers
import com.example.telegramsms.utils.data.GsonRequest
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

const val baseUrl: String = "https://207e-123-21-117-25.ngrok-free.app"
const val forwardSMSPath: String = "/api/v1/sms/forward"
const val xApiKey: String = "Aqswde123@@"

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
    headers["x-api-key"] = xApiKey
    headers["content-type"] = "application/json"

    val request = GsonRequest(
        "$baseUrl$forwardSMSPath",
        clazz = BaseResponse::class.java,
        method = Request.Method.POST,
        headers = headers,
        jsonPayload = payload,
        listener = {
            if (it.code === "200") {
                Log.i(
                    IDENTIFIER,
                    "${DebuggingIdentifiers.ACTION_OR_EVENT_SUCCEEDED} request : ${it.code}"
                )
                onSuccess()
            } else {
                Log.i(
                    IDENTIFIER,
                    "${DebuggingIdentifiers.ACTION_OR_EVENT_FAILED} request : ${it.message}",
                )
                onError()
            }
        },
        errorListener = {
            val response = it.networkResponse
            try {
                Log.i(
                    IDENTIFIER,
                    "${DebuggingIdentifiers.ACTION_OR_EVENT_FAILED}",
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