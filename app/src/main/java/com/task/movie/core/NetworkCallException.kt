package com.task.movie.core

sealed class NetworkCallException(message: String) : Exception(message) {
    sealed class ClientException(message: String = "") : NetworkCallException(message) {
        class Timeout : ClientException()
        class NoInternetException : ClientException()
    }

    sealed class ServerResponseException(message: String, val code: Int, val body: String?) :
        NetworkCallException(message) {
        class ResourceNotFound(message: String, code: Int, body: String?) : ServerResponseException(message, code, body)
        class AuthenticationFailed(message: String, code: Int, body: String?) :
            ServerResponseException(message, code, body)

        class BadRequest(message: String, code: Int, body: String?) : ServerResponseException(message, code, body)
    }

    class GeneralNetworkError(message: String?) : NetworkCallException(message ?: "")

}
