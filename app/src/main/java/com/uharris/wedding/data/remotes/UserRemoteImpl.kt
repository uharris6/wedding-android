package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.data.services.UserService
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(private val service: UserService) : UserRemote {
    override fun registerUser(userBody: UserBody): Deferred<User> {
        return service.registerUser(userBody)
    }
}