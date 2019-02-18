package com.uharris.wedding.data.services

import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("users")
    fun registerUser(@Body userBody: UserBody): Deferred<User>
}