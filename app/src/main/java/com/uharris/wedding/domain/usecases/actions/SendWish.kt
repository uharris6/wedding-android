package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.domain.executor.PostExecutionThread
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import com.uharris.wedding.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class SendWish @Inject constructor(
    private val wishesRemote: WishesRemote,
    postExecutionThread: PostExecutionThread):
    ObservableUseCase<Wish, SendWish.Params?>(postExecutionThread) {
    public override fun buildUseCaseObservable(params: SendWish.Params?): Observable<Wish> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return wishesRemote.sendWish(WishBody(params.userId, params.comment))
    }

    data class Params constructor(val userId: String, val comment: String) {
        companion object {
            fun SendWish(userId: String, comment: String): Params {
                return Params(userId, comment)
            }
        }
    }
}