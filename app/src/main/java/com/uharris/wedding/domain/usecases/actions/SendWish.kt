package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import com.uharris.wedding.domain.usecases.base.UseCase
import javax.inject.Inject

class SendWish @Inject constructor(private val wishesRemote: WishesRemote) : UseCase<Wish, SendWish.Params>() {

    override suspend fun run(params: Params): Result<Wish> =
        wishesRemote.sendWish(WishBody(params.userId, params.comment))

    data class Params(val userId: String, val comment: String)
}