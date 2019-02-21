package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.base.UseCase
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPhoto @Inject constructor(private val photosRemote: PhotosRemote): UseCase<Photo, UploadPhoto.Params>() {
    override suspend fun run(params: Params): Result<Photo> =
        photosRemote.sendPhoto(params.title, params.path)

    data class Params(val title: String, val path: String)


}