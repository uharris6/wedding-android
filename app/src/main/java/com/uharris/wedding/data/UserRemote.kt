package com.uharris.wedding.data

import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import kotlinx.coroutines.Deferred

interface UserRemote {
    fun registerUser(userBody: UserBody): Deferred<User>
}