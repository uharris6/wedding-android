package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.domain.executor.PostExecutionThread
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class FetchWishes @Inject constructor(
    private val wishesRemote: WishesRemote,
    postExecutionThread: PostExecutionThread):
    ObservableUseCase<List<Wish>, Nothing?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Wish>> {
        return wishesRemote.getWishes()
    }

}