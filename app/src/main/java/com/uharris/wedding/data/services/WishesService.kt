package com.uharris.wedding.data.services

import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WishesService {

    @POST("wishes")
    fun sendWish(@Body body: WishBody): Deferred<Response<Wish>>

    @GET("wishes")
    fun getWishes(): Deferred<Response<List<Wish>>>
}