package com.uharris.wedding.data

import com.uharris.wedding.domain.model.Site
import io.reactivex.Observable

interface SitesRemote {

    fun getSites(): Observable<List<Site>>
}