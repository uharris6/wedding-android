package com.uharris.wedding.data.services

import com.uharris.wedding.domain.model.Photo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PhotosService {

    @Multipart
    @POST("photos")
    fun sendPhoto(@Part("title") title: RequestBody ,@Part photo: MultipartBody.Part): Observable<Photo>

    @GET("photos")
    fun getPhotos(): Observable<List<Photo>>
}