package com.uharris.wedding.presentation.sections.sites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.usecases.actions.FetchSites
import com.uharris.wedding.domain.usecases.base.UseCase
import com.uharris.wedding.presentation.base.BaseViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import javax.inject.Inject

class SitesViewModel @Inject constructor(
    private val fetchSites: FetchSites,
    application: Application
): BaseViewModel(application) {
    val liveData: MutableLiveData<Resource<List<Site>>> = MutableLiveData()

     fun fetchSites(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        fetchSites(UseCase.None()){it.either(::handleFailure, ::handleSiteList)}
        return
     }

    private fun handleSiteList(sites: List<Site>) {
        liveData.postValue(Resource(ResourceState.SUCCESS, sites, null))
    }
}