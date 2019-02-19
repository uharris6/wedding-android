package com.uharris.wedding.presentation.sections.photos.add

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.UploadPhoto
import com.uharris.wedding.presentation.base.BaseViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import javax.inject.Inject

class AddPhotoViewModel @Inject constructor(
    private val uploadPhoto: UploadPhoto,
    application: Application
) : BaseViewModel(application) {

    val photoLiveData: MutableLiveData<Resource<Photo>> = MutableLiveData()

    fun uploadPhoto(title: String, path: String) {
        photoLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        uploadPhoto(UploadPhoto.Params(title, path)){it.either(::handleFailure, ::handlePhotoUploaded)}
        return
    }

    private fun handlePhotoUploaded(photo: Photo) {
        photoLiveData.postValue(Resource(ResourceState.SUCCESS, photo, null))
    }


}