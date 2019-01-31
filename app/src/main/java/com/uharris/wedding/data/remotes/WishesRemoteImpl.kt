package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.services.WishesService
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import io.reactivex.Observable
import javax.inject.Inject

class WishesRemoteImpl @Inject constructor(private val service: WishesService): WishesRemote {
    override fun sendWish(wishBody: WishBody): Observable<Wish> {
        return service.sendWish(wishBody)
    }

    override fun getWishes(): Observable<List<Wish>> {
        return service.getWishes()
    }
}