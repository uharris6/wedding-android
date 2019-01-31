package com.uharris.wedding.data

import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import io.reactivex.Observable

interface WishesRemote {
    fun sendWish(wishBody: WishBody): Observable<Wish>

    fun getWishes(): Observable<List<Wish>>
}