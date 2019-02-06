package com.uharris.wedding.data

import com.uharris.wedding.domain.model.Photo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PhotosRemote {

    fun sendPhoto(title: RequestBody, photo: MultipartBody.Part): Observable<Photo>

    fun getPhotos(): Observable<List<Photo>>
}