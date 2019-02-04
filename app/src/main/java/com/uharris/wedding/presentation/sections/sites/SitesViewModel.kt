package com.uharris.wedding.presentation.sections.sites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.usecases.actions.FetchSites
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SitesViewModel @Inject constructor(
    private val fetchSites: FetchSites,
    application: Application
): AndroidViewModel(application) {
    val liveData: MutableLiveData<Resource<List<Site>>> = MutableLiveData()

    fun fetchSites(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))

        return fetchSites.execute(FetchSitesSubscriber())
    }

    override fun onCleared() {
        fetchSites.dispose()
        super.onCleared()
    }

    inner class FetchSitesSubscriber : DisposableObserver<List<Site>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Site>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }
}