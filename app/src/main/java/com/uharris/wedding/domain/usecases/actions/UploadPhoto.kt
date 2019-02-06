package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.domain.executor.PostExecutionThread
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPhoto @Inject constructor(
    private val photosRemote: PhotosRemote,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<Photo, UploadPhoto.Params>(postExecutionThread)  {
    override fun buildUseCaseObservable(params: UploadPhoto.Params?): Observable<Photo> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return photosRemote.sendPhoto(RequestBody.create(okhttp3.MultipartBody.FORM, params.title), createMultipartBody(params.path))
    }

    data class Params constructor(val path: String, val title: String) {
        companion object {
            fun UploadPhoto(path: String, title: String): Params {
                return Params(path, title)
            }
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