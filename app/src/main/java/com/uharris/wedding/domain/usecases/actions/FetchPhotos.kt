package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.base.UseCase
import javax.inject.Inject

class FetchPhotos @Inject constructor(private val photosRemote: PhotosRemote): UseCase<List<Photo>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Photo>> = photosRemote.getPhotos()
}