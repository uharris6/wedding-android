package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.SitesRemote
import com.uharris.wedding.domain.executor.PostExecutionThread
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class FetchSites @Inject constructor(
    private val sitesRemote: SitesRemote,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<List<Site>, Nothing?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Site>> {
        return sitesRemote.getSites()
    }
}