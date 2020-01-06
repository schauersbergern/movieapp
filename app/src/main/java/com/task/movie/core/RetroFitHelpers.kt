package com.task.movie.core

import com.github.kittinunf.result.coroutines.SuspendableResult
import okhttp3.ResponseBody
import retrofit2.Call
import java.net.HttpURLConnection
import java.net.SocketTimeoutException


suspend fun <T : Any> handleRetrofitCall(call: Call<T>) = SuspendableResult.of<T, Exception> {
    val result = try {
        call.execute()
    } catch (error: Exception) {
        throw handleCaughtError(error)
    }

    if (!result.isSuccessful) {
        throw handleNetworkError(result.code(), result.message(), result.errorBody())
    }

    result.body()!!
}

suspend fun handleEmptyRetrofitCall(call: Call<Void>) = SuspendableResult.of<Unit, Exception> {
    val result = try {
        call.execute()
    } catch (error: Exception) {
        throw(handleCaughtError(error))
    }

    if (!result.isSuccessful) {
        throw handleNetworkError(result.code(), result.message(), result.errorBody())
    }
}

fun handleCaughtError(error: Exception): NetworkCallException {
    return when (error) {
        is NetworkCallException -> error
        is SocketTimeoutException -> NetworkCallException.ClientException.Timeout()
        else -> NetworkCallException.GeneralNetworkError(error.message)
    }
}

fun handleNetworkError(code: Int, message: String, body: ResponseBody?): NetworkCallException {
    return when (code) {
        HttpURLConnection.HTTP_NOT_FOUND -> NetworkCallException.ServerResponseException.ResourceNotFound(
            message,
            code,
            body?.string()
        )
        HttpURLConnection.HTTP_UNAUTHORIZED,
        HttpURLConnection.HTTP_FORBIDDEN -> NetworkCallException.ServerResponseException.AuthenticationFailed(
            message,
            code,
            body?.string()
        )

        HttpURLConnection.HTTP_BAD_REQUEST -> NetworkCallException.ServerResponseException.BadRequest(
            message,
            code,
            body?.string()
        )
        else -> NetworkCallException.GeneralNetworkError(message)
    }
}