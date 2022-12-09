package com.example.devicefinder2.util

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * parses error response body
 */
object ErrorUtils {

    fun parseError(response: Response<*>, retrofit: Retrofit): Error? {
        val converter = retrofit.responseBodyConverter<Error>(Error::class.java, arrayOfNulls(0))
        return try {
            converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            Error()
        }
    }
}

data class Error(val status_code: Int = 0,
                 val status_message: String? = null)