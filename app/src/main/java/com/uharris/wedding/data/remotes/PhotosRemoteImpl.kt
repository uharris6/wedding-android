package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.BaseRepository
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.data.services.PhotosService
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.presentation.base.NetworkHandler
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PhotosRemoteImpl @Inject constructor(private val networkHandler: NetworkHandler,
                                           private val service: PhotosService): BaseRepository(), PhotosRemote {
    override suspend fun sendPhoto(title: String, photo: String): Result<Photo> {
        return when(networkHandler.isConnected){
            true -> safeApiCall(service.sendPhoto(RequestBody.create(MultipartBody.FORM, title), createMultipartBody(photo)), default = Photo())
            false, null -> Result.Error(IOException("Error connecting to the network"))
        }
    }

    override suspend fun getPhotos(): Result<List<Photo>> {
        return when(networkHandler.isConnected){
            true -> safeApiCall(service.getPhotos(), default = emptyList())
            false, null -> Result.Error(IOException("Error connecting to the network"))
        }
    }

    private fun createMultipartBody(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val requestBody = createRequestBody(file)
        return MultipartBody.Part.createFormData("photo", file.name, requestBody)
    }

    private fun createRequestBody(file: File): RequestBody {
        return RequestBody.create(MediaType.parse("image/*"), file)
    }
}