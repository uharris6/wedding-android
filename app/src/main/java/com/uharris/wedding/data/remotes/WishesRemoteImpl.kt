package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.base.BaseRepository
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.data.services.WishesService
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import com.uharris.wedding.presentation.base.NetworkHandler
import java.io.IOException
import javax.inject.Inject

class WishesRemoteImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val service: WishesService): BaseRepository(), WishesRemote {
    override suspend fun sendWish(wishBody: WishBody): Result<Wish> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(service.sendWish(wishBody), default = Wish())
            false, null -> Result.Error(IOException("Error connecting to the network"))
        }
    }

    override suspend fun getWishes(): Result<List<Wish>> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(service.getWishes(), default = emptyList())
            false, null -> Result.Error(IOException("Error connecting to the network"))
        }
    }
}