package com.uharris.wedding.data

import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import kotlinx.coroutines.Deferred

interface UserRemote {
    suspend fun registerUser(userBody: UserBody): Either<Failure, User>
}