package com.uharris.wedding.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(
    application: Application) : AndroidViewModel(application) {

    var failure: MutableLiveData<String> = MutableLiveData()

    protected fun handleFailure(exception: Exception) {
        this.failure.value = exception.message
    }
}