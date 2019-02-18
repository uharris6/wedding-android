package com.uharris.wedding.presentation.sections.photos.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import javax.inject.Inject

class AddPhotoViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    val photoLiveData: MutableLiveData<Resource<Photo>> = MutableLiveData()

    fun uploadPhoto(title: String, path: String) {
        photoLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return
    }

    override fun onCleared() {
        super.onCleared()
    }


}