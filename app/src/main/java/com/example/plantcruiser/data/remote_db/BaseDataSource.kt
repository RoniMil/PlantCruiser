package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.utils.Resource
import retrofit2.Response

// Class for handling network calls and managing their responses
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {

        try {
            val result = call()
            if (result.isSuccessful) {
                val body = result.body()
                if (body != null) return Resource.success(body)
            }
            return Resource.error(
                "Network call has failed for the following reason: " +
                        "${result.message()} ${
                            result.code()
                        }"
            )
        } catch (e: Exception) {
            return Resource.error(
                "Network call has failed for the following reason: "
                        + (e.localizedMessage ?: e.toString())
            )
        }
    }
}
