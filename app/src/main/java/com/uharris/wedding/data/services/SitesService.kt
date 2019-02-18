package com.uharris.wedding.data.services

import com.uharris.wedding.domain.model.Site
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface SitesService {

    @GET("sites")
    fun getSites(): Deferred<Response<List<Site>>>
}