package com.uharris.wedding.presentation.sections.photos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.FetchPhotos
import com.uharris.wedding.domain.usecases.base.UseCase
import com.uharris.wedding.presentation.base.BaseViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val fetchPhotos: FetchPhotos,
    application: Application): BaseViewModel(application) {

    val photosLiveData: MutableLiveData<Resource<List<Photo>>> = MutableLiveData()

    fun fetchPhotos(){
        photosLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        fetchPhotos(UseCase.None()){
           when(it){
               is Result.Success -> handlePhotoList(it.data)
               is Result.Error -> handleFailure(it.exception)
           }
        }
        return
    }

    private fun handlePhotoList(photos: List<Photo>) {
        photosLiveData.postValue(Resource(ResourceState.SUCCESS, photos, null))
    }
}