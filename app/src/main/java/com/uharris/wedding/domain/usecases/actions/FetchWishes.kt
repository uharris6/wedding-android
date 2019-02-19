package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.base.UseCase
import javax.inject.Inject

class FetchWishes @Inject constructor(private val wishesRemote: WishesRemote): UseCase<List<Wish>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<Wish>> = wishesRemote.getWishes()
}