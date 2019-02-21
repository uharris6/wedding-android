package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.data.base.BaseRepository
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.data.services.UserService
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import com.uharris.wedding.presentation.base.NetworkHandler
import java.io.IOException
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val service: UserService): BaseRepository(), UserRemote {
    override suspend fun registerUser(userBody: UserBody): Result<User> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(service.registerUser(userBody), default = User())
            false, null -> Result.Error(IOException("Error connecting to the network"))
        }
    }
}