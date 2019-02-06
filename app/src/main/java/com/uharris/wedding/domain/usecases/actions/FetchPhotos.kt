package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.domain.executor.PostExecutionThread
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class FetchPhotos@Inject constructor(
    private val photosRemote: PhotosRemote,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<List<Photo>, Nothing?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Photo>> {
        return photosRemote.getPhotos()
    }
}