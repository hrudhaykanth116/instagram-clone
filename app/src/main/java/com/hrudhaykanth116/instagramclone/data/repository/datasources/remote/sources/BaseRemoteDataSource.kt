package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources


import android.util.Log
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.utils.network.ConnectionStateMonitor
import retrofit2.Response

abstract class BaseRemoteDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        if (ConnectionStateMonitor.isConnected) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return Resource.success(body)
                }
                val apiError = Resource.Error(
                    message = response.message(),
                    statusCode = response.code(),
                    code = null,
                    description = response.errorBody().toString()
                )
                return Resource.error(
                    response.message(),
                    null,
                    apiError
                )
            } catch (e: Exception) {
                Log.e(TAG, "getResult: ", e)
                return error(e.message ?: e.toString(), null)
            }
        } else {
            Log.e(TAG, "getResult: isConnected = false")
            return Resource.error(
                "You are offline.",
                null,
                Resource.Error(
                    "You are offline",
                    Resource.ErrorCode.NO_INTERNET,
                    -1,
                    null,
                )
            )
        }
    }

    private fun <T> error(message: String, error: Resource.Error?): Resource<T> {
        return Resource.error(
            "Network call has failed for a following reason: $message",
            null,
            error
        )
    }

    companion object {
        private const val TAG = "BaseDataSource"
    }

}