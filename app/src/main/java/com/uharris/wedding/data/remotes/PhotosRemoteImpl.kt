package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.BaseRepository
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.data.services.PhotosService
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.presentation.base.NetworkHandler
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PhotosRemoteImpl @Inject constructor(private val networkHandler: NetworkHandler,
                                           private val service: PhotosService): BaseRepository(), PhotosRemote {
    override suspend fun sendPhoto(title: RequestBody, photo: MultipartBody.Part): Either<Failure, Photo> {
        return when(networkHandler.isConnected){
            true -> safeApiCall(call = {service.sendPhoto(title, photo).await()}, default = Photo())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    override suspend fun getPhotos(): Either<Failure, List<Photo>> {
        return when(networkHandler.isConnected){
            true -> safeApiCall(call = {service.getPhotos().await()}, default = emptyList())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }
}