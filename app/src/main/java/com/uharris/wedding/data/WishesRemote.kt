package com.uharris.wedding.data

import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody

interface WishesRemote {
    suspend fun sendWish(wishBody: WishBody): Either<Failure, Wish>

    suspend fun getWishes(): Either<Failure, List<Wish>>
}