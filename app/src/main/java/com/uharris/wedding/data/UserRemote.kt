package com.uharris.wedding.data

import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import io.reactivex.Observable

interface UserRemote {
    fun registerUser(userBody: UserBody): Observable<User>
}