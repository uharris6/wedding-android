package com.uharris.wedding.presentation.sections.photos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.FetchPhotos
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    val fetchPhotos: FetchPhotos,
    application: Application): AndroidViewModel(application) {

    val photosLiveData: MutableLiveData<Resource<List<Photo>>> = MutableLiveData()

    fun fetchPhotos(){
        photosLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        return fetchPhotos.execute(FetchPhotosSubscriber())
    }

    override fun onCleared() {
        fetchPhotos.dispose()
        super.onCleared()
    }

    inner class FetchPhotosSubscriber : DisposableObserver<List<Photo>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Photo>) {
            photosLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            photosLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }
}