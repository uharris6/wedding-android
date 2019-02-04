package com.uharris.wedding.data.services

import com.uharris.wedding.domain.model.Site
import io.reactivex.Observable
import retrofit2.http.GET

interface SitesService {

    @GET("sites")
    fun getSites(): Observable<List<Site>>
}