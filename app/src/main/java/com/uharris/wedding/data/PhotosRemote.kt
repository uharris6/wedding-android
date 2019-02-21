package com.uharris.wedding.data

import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Photo

interface PhotosRemote {

    suspend fun sendPhoto(title: String, photo: String): Result<Photo>

    suspend fun getPhotos(): Result<List<Photo>>
}