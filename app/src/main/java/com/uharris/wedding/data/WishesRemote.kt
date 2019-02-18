package com.uharris.wedding.data

import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import kotlinx.coroutines.Deferred

interface WishesRemote {
    fun sendWish(wishBody: WishBody): Deferred<Wish>

    fun getWishes(): Deferred<List<Wish>>
}