package com.uharris.wedding.data

import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody

interface UserRemote {
    suspend fun registerUser(userBody: UserBody): Result<User>
}