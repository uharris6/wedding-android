package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.SitesRemote
import com.uharris.wedding.data.services.SitesService
import com.uharris.wedding.domain.model.Site
import io.reactivex.Observable
import javax.inject.Inject

class SitesRemoteImpl @Inject constructor(private val service: SitesService): SitesRemote {
    override fun getSites(): Observable<List<Site>> {
        return service.getSites()
    }
}