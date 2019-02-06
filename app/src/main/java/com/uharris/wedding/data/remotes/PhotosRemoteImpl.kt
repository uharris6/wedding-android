package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.services.PhotosService
import com.uharris.wedding.domain.model.Photo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PhotosRemoteImpl @Inject constructor(private val service: PhotosService): PhotosRemote {
    override fun sendPhoto(title: RequestBody, photo: MultipartBody.Part): Observable<Photo> {
        return service.sendPhoto(title, photo)
    }

    override fun getPhotos(): Observable<List<Photo>> {
        return service.getPhotos()
    }
}