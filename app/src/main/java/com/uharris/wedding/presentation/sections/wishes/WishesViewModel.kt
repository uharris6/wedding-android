package com.uharris.wedding.presentation.sections.wishes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.actions.FetchWishes
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class WishesViewModel @Inject constructor(
    private val fetchWishes: FetchWishes,
    application: Application): AndroidViewModel(application) {

    val liveData: MutableLiveData<Resource<List<Wish>>> = MutableLiveData()

    fun fetchWishes(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))

        return fetchWishes.execute(FetchWishesSubscriber())
    }

    override fun onCleared() {
        fetchWishes.dispose()
        super.onCleared()
    }

    inner class FetchWishesSubscriber : DisposableObserver<List<Wish>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Wish>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }
}