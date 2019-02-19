package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.base.BaseRepository
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.data.services.WishesService
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import com.uharris.wedding.presentation.base.NetworkHandler
import javax.inject.Inject

class WishesRemoteImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val service: WishesService): BaseRepository(), WishesRemote {
    override suspend fun sendWish(wishBody: WishBody): Either<Failure, Wish> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(call = {service.sendWish(wishBody).await()}, default = Wish())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    override suspend fun getWishes(): Either<Failure, List<Wish>> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(call = {service.getWishes().await()}, default = emptyList())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }
}