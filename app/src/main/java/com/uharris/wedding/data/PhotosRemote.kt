package com.uharris.wedding.data

import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Photo
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PhotosRemote {

    suspend fun sendPhoto(title: RequestBody, photo: MultipartBody.Part): Either<Failure, Photo>

    suspend fun getPhotos(): Either<Failure, List<Photo>>
}