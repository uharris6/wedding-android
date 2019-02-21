package com.uharris.wedding.data

import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody

interface WishesRemote {
    suspend fun sendWish(wishBody: WishBody): Result<Wish>

    suspend fun getWishes(): Result<List<Wish>>
}