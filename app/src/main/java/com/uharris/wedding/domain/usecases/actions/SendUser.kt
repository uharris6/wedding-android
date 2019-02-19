package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import com.uharris.wedding.domain.usecases.base.UseCase
import javax.inject.Inject

class SendUser @Inject constructor(private val userRemote: UserRemote) : UseCase<User, SendUser.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> =
        userRemote.registerUser(UserBody(params.firstName, params.nickName, params.lastName))

    data class Params(val firstName: String, val nickName: String, val lastName: String)
}