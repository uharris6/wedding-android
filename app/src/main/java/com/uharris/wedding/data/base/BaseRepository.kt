package com.uharris.wedding.data.base

import com.uharris.wedding.data.functional.Either
import retrofit2.Response

open class BaseRepository {

    suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>, default: T): Either<Failure, T> {
        return try {
            val response = call.invoke()
            when (response.isSuccessful) {
                true -> Either.Right((response.body() ?: default))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}