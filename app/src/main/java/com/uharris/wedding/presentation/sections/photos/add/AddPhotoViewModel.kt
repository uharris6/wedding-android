package com.uharris.wedding.presentation.sections.photos.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.UploadPhoto
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class AddPhotoViewModel @Inject constructor(
    private var uploadPhoto: UploadPhoto,
    application: Application
) : AndroidViewModel(application) {

    val photoLiveData: MutableLiveData<Resource<Photo>> = MutableLiveData()

    fun uploadPhoto(title: String, path: String) {
        photoLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val params = UploadPhoto.Params(path, title)
        return uploadPhoto.execute(UploadPhotoSubscriber(), params)
    }

    override fun onCleared() {
        uploadPhoto.dispose()
        super.onCleared()
    }

    inner class UploadPhotoSubscriber: DisposableObserver<Photo>() {
        override fun onComplete() {}

        override fun onNext(t: Photo) {
            photoLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            photoLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }
}