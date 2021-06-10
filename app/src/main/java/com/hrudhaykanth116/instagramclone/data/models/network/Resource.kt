package com.hrudhaykanth116.instagramclone.data.models.network

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val error: Error?
) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, data: T? = null, error: Error?): Resource<T> {
            return Resource(Status.ERROR, data, message, error)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }

    class Error(
        var message: String? = null,
        var code: ErrorCode? = null,
        var statusCode: Int? = null,
        var description: String?= null,
    )

    enum class ErrorCode{
        NO_INTERNET
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}