package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.base.UseCase
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPhoto @Inject constructor(private val photosRemote: PhotosRemote): UseCase<Photo, UploadPhoto.Params>() {
    override suspend fun run(params: Params): Either<Failure, Photo> =
        photosRemote.sendPhoto(RequestBody.create(okhttp3.MultipartBody.FORM, params.title), createMultipartBody(params.path))

    data class Params(val title: String, val path: String)

    private fun createMultipartBody(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val requestBody = createRequestBody(file)
        return MultipartBody.Part.createFormData("photo", file.name, requestBody)
    }

    private fun createRequestBody(file: File): RequestBody {
        return RequestBody.create(MediaType.parse("image/*"), file)
    }
}